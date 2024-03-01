package pl.gpw.wats.client.md;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import pl.gpw.wats.client.EncryptionUtils;
import pl.gpw.wats.client.md.bendec.BendecUtils;
import pl.gpw.wats.client.md.bendec.Header;
import pl.gpw.wats.client.md.bendec.MsgType;

public class OnlineMarketDataClient {
    private final DatagramChannel dc;
    private final MembershipKey membershipKey;
    private final ConcurrentLinkedQueue<ByteBuffer> buffer = new ConcurrentLinkedQueue<>();
    private final Integer msgBufferSize;
    private final EncryptionUtils encryptionUtils;
    private final Map<MsgType, LinkedList<Consumer<byte[]>>> handlers;
    private final LinkedList<Consumer<byte[]>> generalHandlers;

    public OnlineMarketDataClient(MdConnectionConfig config, EncryptionUtils encryptionUtils, Integer rcvBuffer, Integer msgBuffer) throws IOException {
        this.handlers = new HashMap<>();
        this.generalHandlers = new LinkedList<>();
        this.msgBufferSize = msgBuffer;
        this.encryptionUtils = encryptionUtils;

        NetworkInterface ni = NetworkInterface.getByName(config.interfaceName());
        InetAddress group = InetAddress.getByName(config.multicastGroupIp());

        dc = DatagramChannel.open(StandardProtocolFamily.INET)
                .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                .bind(new InetSocketAddress(config.port()))
                .setOption(StandardSocketOptions.IP_MULTICAST_IF, ni)
                .setOption(StandardSocketOptions.SO_RCVBUF, rcvBuffer);
        dc.configureBlocking(false);
        membershipKey = dc.join(group, ni);
    }

    /**
     * Message type handler receives messages with specified MsgType
     *
     * @param type
     * @param consumer
     */
    public void addHandler(MsgType type, Consumer consumer) {
        if (!handlers.containsKey(type))
            handlers.put(type, new LinkedList<>());
        handlers.get(type).add(consumer);
    }

    /**
     * General handler received all messages
     *
     * @param consumer
     */
    public void addGeneralHandler(Consumer consumer) {
        generalHandlers.add(consumer);
    }

    /**
     * Receive UDP Datagrams
     * 
     * @throws IOException
     */
    public void receive() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(msgBufferSize);
        if(dc.receive(byteBuffer) != null)
            buffer.add(byteBuffer);
    }

    /**
     * Consume message from buffer
     * 
     * @throws Exception
     */
    public void read() throws Exception {
        ByteBuffer buffer = this.buffer.poll();
        if (buffer == null)
            return;
        buffer.flip();
        buffer.mark();
        byte x[] = new byte[2];
        buffer.get(x, 0, 2);
        int length = BendecUtils.uInt16FromByteArray(x, 0);
        buffer.reset();
        if (length > buffer.remaining())
            throw new Exception("Uncomplete message");
        byte[] messageBytes = new byte[length];
        buffer.get(messageBytes, 0, length);

        Header header = new Header(messageBytes);
        if (header.getIsEncrypted()) {
            encryptionUtils.decrypt(header, messageBytes);
        }
        generalHandlers.forEach(handler -> handler.accept(messageBytes));
        if (handlers.containsKey(header.getMsgType()))
            handlers.get(header.getMsgType()).forEach(handler -> handler.accept(messageBytes));
    }

}
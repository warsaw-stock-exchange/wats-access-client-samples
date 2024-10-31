package pl.gpw.wats.client.md;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import pl.gpw.wats.client.EncryptionUtils;
import pl.gpw.wats.client.md.bendec.*;
import pl.gpw.wats.client.replay.bendec.ReplayRequest;

public class OnlineMarketDataReplayClient {
    private final SocketChannel client;
    private final ByteBuffer buffer;
    private final Map<MsgType, LinkedList<Consumer<byte[]>>> handlers;
    private final LinkedList<Consumer<byte[]>> generalHandlers;
    private final EncryptionUtils encryptionUtils;

    public OnlineMarketDataReplayClient(MdReplayConnectionConfig config, EncryptionUtils encryptionUtils, int bufferSize, boolean blocking) throws IOException {
        this.encryptionUtils = encryptionUtils;
        client = SocketChannel.open(new InetSocketAddress(config.hostname(), config.port()));
        client.configureBlocking(blocking);
        buffer = ByteBuffer.allocateDirect(bufferSize);
        buffer.clear();
        handlers = new HashMap<>();
        generalHandlers = new LinkedList<>();
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

    public void send(ReplayRequest request) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(request.toBytes());
        while (buffer.hasRemaining()) {
            client.write(buffer);
        }
    }

     /**
     * Reads all complete messages form the buffer
     *
     * @return false if reached EOF, true otherwise
     * @throws IOException
     */
    public boolean read() throws Exception {
        int read = client.read(buffer);
        if (read == -1) {
            client.close();
            return false;
        } else if (read > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                // Replay stream is finished with two bytes zero pair
                if (buffer.remaining() < 4) {
                    break;
                }
                byte x[] = new byte[4];
                buffer.mark();
                buffer.get(x, 0, 4);
                int length = BendecUtils.uInt16FromByteArray(x, 0);
                buffer.reset();
                if (length > buffer.remaining())
                    break;
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
            buffer.compact();
        }
        return true;
    }
}

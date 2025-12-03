package pl.gpw.wats.client.md;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import pl.gpw.wats.client.EncryptionUtils;
import pl.gpw.wats.client.md.bendec.BendecUtils;
import pl.gpw.wats.client.md.bendec.ByteSerializable;
import pl.gpw.wats.client.md.bendec.EncryptionKey;
import pl.gpw.wats.client.md.bendec.Header;
import pl.gpw.wats.client.md.bendec.Login;
import pl.gpw.wats.client.md.bendec.MessageFilter;
import pl.gpw.wats.client.md.bendec.MessageFilter.MessageFilterOptions;
import pl.gpw.wats.client.md.bendec.MsgType;

public class OnlineMarketDataSnapshotClient {
    private final MdSnapshotConnectionConfig config;
    private final SocketChannel client;
    private final ByteBuffer buffer;
    private final Map<MsgType, LinkedList<Consumer<byte[]>>> handlers;
    private final LinkedList<Consumer<byte[]>> generalHandlers;
    private EncryptionUtils encryptionUtils;

    public OnlineMarketDataSnapshotClient(MdSnapshotConnectionConfig config, int bufferSize, boolean blocking) throws IOException {
        this.config = config;
        client = SocketChannel.open(new InetSocketAddress(config.hostname(), config.port()));
        client.configureBlocking(blocking);
        buffer = ByteBuffer.allocateDirect(bufferSize);
        buffer.clear();
        encryptionUtils = new EncryptionUtils(config.nonce());
        handlers = new HashMap<>();
        generalHandlers = new LinkedList<>();
        addHandler(MsgType.ENCRYPTIONKEY, encryptionKeyHandler);
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

    public void send(ByteSerializable msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.toBytes());
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

    public void login() throws IOException {
        send(loginMessage());
    }

    public EncryptionUtils getEncryptionUtils() {
        return encryptionUtils;
    }

    private Login loginMessage() {
        pl.gpw.wats.client.md.bendec.Header header = new pl.gpw.wats.client.md.bendec.Header(
                Login.byteLength, MsgType.LOGIN, config.version(), 0, BigInteger.ZERO, BigInteger.ZERO, false, BigInteger.ONE, 0L, 0, 0);
        return new Login(header, config.connectionId(), config.token(), new MessageFilter(MessageFilterOptions.TRADES.getOptionValue() | MessageFilterOptions.TRADES.getOptionValue()));
    }

    private Consumer<byte[]> encryptionKeyHandler = (bytes) -> {
        encryptionUtils.add(new EncryptionKey(bytes));
    };
}

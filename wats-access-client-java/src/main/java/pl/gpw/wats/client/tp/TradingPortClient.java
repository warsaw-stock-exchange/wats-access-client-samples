package pl.gpw.wats.client.tp;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import pl.gpw.wats.client.tp.bendec.*;

/**
 * Sample Trading Port Client
 * Warn! It is not thread safe!
 */
public class TradingPortClient {
    private final SocketChannel client;
    private final ByteBuffer buffer;
    private final Map<MsgType, LinkedList<Consumer<byte[]>>> handlers;
    private final LinkedList<Consumer<byte[]>> generalHandlers;
    private ConnectionConfig config;
    private ConnectionStatus connectionStatus;

    public TradingPortClient(ConnectionConfig config, int bufferSize, boolean blocking) throws IOException {
        this.config = config;
        this.connectionStatus = new ConnectionStatus();
        client = SocketChannel.open(new InetSocketAddress(config.hostname(), config.port()));
        client.configureBlocking(blocking);
        buffer = ByteBuffer.allocateDirect(bufferSize);
        buffer.clear();
        handlers = new HashMap<>();
        generalHandlers = new LinkedList<>();
        addHandler(MsgType.LOGINRESPONSE, loginResponseHandler);
        addHandler(MsgType.CONNECTIONCLOSE, logoutAndCloseHandler);
        addHandler(MsgType.LOGOUTRESPONSE, logoutAndCloseHandler);
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
     * General handler receives all messages
     *
     * @param consumer
     */
    public void addGeneralHandler(Consumer consumer) {
        generalHandlers.add(consumer);
    }

    /**
     * Remove general handler
     * @param consumer handler
     */
    public void removeGeneralHandler(Consumer consumer) {
        generalHandlers.remove(consumer);
    }

    public void login() throws IOException {
        send_session(loginMessage());
    }

    public void logout() throws IOException {
        send_session(logoutMessage());
    }

    public void close() throws IOException {
        client.close();
    }

    /**
     * Send single session message
     *
     * @param message
     * @return true if sent successfully, false otherwise
     * @throws IOException
     */
    public boolean send_session(ByteSerializable message) throws IOException {
        if (message instanceof Message) {
            if (message instanceof WithSessionId) {
                ((WithSessionId) message).setSessionId(connectionStatus.getSessionId());
            }
        }
        ByteBuffer buffer = ByteBuffer.wrap(message.toBytes());
        while (buffer.hasRemaining()) {
            client.write(buffer);
        }
        return true;
    }

    /**
     * Send single message
     *
     * @param message
     * @return true if sent successfully, false otherwise
     * @throws IOException
     */
    public boolean send(ByteSerializable message) throws IOException {
        if (message instanceof Message) {
            Long seqNum = connectionStatus.getNextExpectedSeqNumAndIncrement();
            ((Message) message).getHeader().setSeqNum(seqNum);
            if (message instanceof WithSessionId) {
                ((WithSessionId) message).setSessionId(connectionStatus.getSessionId());
            }
        }
        ByteBuffer buffer = ByteBuffer.wrap(message.toBytes());
        while (buffer.hasRemaining()) {
            client.write(buffer);
        }
        return true;
    }

    /**
     * Reads all complete messages form the buffer
     *
     * @return false if reached EOF, true otherwise
     * @throws IOException
     */
    public boolean read() throws IOException {
        int read = client.read(buffer);
        if (read == -1) {
            client.close();
            return false;
        } else if (read > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                if (buffer.remaining() < 2) {
                    break;
                }
                byte x[] = new byte[2];
                buffer.mark();
                buffer.get(x, 0, 2);
                int length = BendecUtils.uInt16FromByteArray(x, 0);
                buffer.reset();
                if (length > buffer.remaining())
                    break;
                byte[] messageBytes = new byte[length];
                buffer.get(messageBytes, 0, length);

                MsgType type = Message.getMsgType(messageBytes);

                generalHandlers.forEach(handler -> handler.accept(messageBytes));
                if (handlers.containsKey(type))
                    handlers.get(type).forEach(handler -> handler.accept(messageBytes));
            }
            buffer.compact();
        }
        return true;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    private Login loginMessage() {
        pl.gpw.wats.client.tp.bendec.Header header = new pl.gpw.wats.client.tp.bendec.Header(
                Login.byteLength, pl.gpw.wats.client.tp.bendec.MsgType.LOGIN, 0, BigInteger.ZERO, BigInteger.ZERO);
        return new Login(header, config.version(), config.token(), config.connectionId(),
                connectionStatus.getNextExpectedSeqNum(), 0);
    }

    private Logout logoutMessage() {
        pl.gpw.wats.client.tp.bendec.Header header = new pl.gpw.wats.client.tp.bendec.Header(
                Logout.byteLength, MsgType.LOGOUT, 0, BigInteger.ZERO, BigInteger.ZERO);
        return new Logout(header);
    }

    private Consumer<byte[]> loginResponseHandler = (bytes) -> {
        LoginResponse loginResponse = new LoginResponse(bytes);
        connectionStatus.setSessionId(loginResponse.getSessionId());

        if (loginResponse.getResult() == LoginResult.ALREADYLOGGEDIN) {
            connectionStatus.setLogged(false);
        } else if (loginResponse.getResult() == LoginResult.OK) {
            connectionStatus.setNextExpectedSeqNum(loginResponse.getNextExpectedSeqNum());
            connectionStatus.setLogged(true);
            connectionStatus.setLastReplaySeqNum(loginResponse.getLastReplaySeqNum());
        } else {
            connectionStatus.setLogged(false);
        }
    };

    private Consumer<byte[]> logoutAndCloseHandler = (bytes) -> {
        connectionStatus.setLogged(false);
    };

    private Consumer<byte[]> lastRecevidedMsg = (bytes) -> {
        long seqNum = BendecUtils.uInt32FromByteArray(bytes, 3);
        connectionStatus.updateLastReplaySeqNum(seqNum);
    };
}

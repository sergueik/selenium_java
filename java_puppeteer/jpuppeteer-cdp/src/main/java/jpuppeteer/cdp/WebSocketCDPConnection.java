package jpuppeteer.cdp;

import com.alibaba.fastjson.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class WebSocketCDPConnection extends CDPConnection {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketCDPConnection.class);

    private URI uri;

    private WebSocketClient client;

    public WebSocketCDPConnection(URI uri) {
        super(uri.getHost() + ":" + uri.getPort());
        this.uri = uri;
        this.client = new CDPWebSocketClient(this.uri);
    }

    public URI uri() {
        return this.uri;
    }

    @Override
    public void open() throws InterruptedException {
        this.client.connectBlocking();
    }

    @Override
    protected void sendInternal(JSONObject request) {
        this.client.send(request.toJSONString());
    }

    @Override
    public void close() {
        client.close();
        super.close();
    }

    private class CDPWebSocketClient extends WebSocketClient {

        public CDPWebSocketClient(URI uri) {
            super(uri);
            //关闭死连接检查
            super.setConnectionLostTimeout(0);
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            logger.debug("open {}, status={}, message={}", uri, serverHandshake.getHttpStatus(), serverHandshake.getHttpStatusMessage());
        }

        @Override
        public void onMessage(String message) {
            try {
                recv(message);
            } catch (Throwable t) {
                logger.error("process message failed, error={}", t.getMessage(), t);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            logger.debug("close {}, {}, {}, {}", uri, code, reason, remote);
        }

        @Override
        public void onError(Exception e) {
            logger.error("error message={}", e.getMessage(), e);
        }
    }
}

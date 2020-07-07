package jpuppeteer.cdp.cdp.domain;

/**
*/
public class IO {

    private jpuppeteer.cdp.CDPSession session;

    public IO(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Close the stream, discard any temporary backing storage.
    */
    public void close(jpuppeteer.cdp.cdp.entity.io.CloseRequest request, int timeout) throws Exception {
        session.send("IO.close", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClose(jpuppeteer.cdp.cdp.entity.io.CloseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IO.close", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Read a chunk of the stream
    */
    public jpuppeteer.cdp.cdp.entity.io.ReadResponse read(jpuppeteer.cdp.cdp.entity.io.ReadRequest request, int timeout) throws Exception {
        return session.send("IO.read", request, jpuppeteer.cdp.cdp.entity.io.ReadResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.io.ReadResponse> asyncRead(jpuppeteer.cdp.cdp.entity.io.ReadRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IO.read", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.io.ReadResponse>(future, jpuppeteer.cdp.cdp.entity.io.ReadResponse.class);
    }

    /**
    * Return UUID of Blob object specified by a remote object id.
    */
    public jpuppeteer.cdp.cdp.entity.io.ResolveBlobResponse resolveBlob(jpuppeteer.cdp.cdp.entity.io.ResolveBlobRequest request, int timeout) throws Exception {
        return session.send("IO.resolveBlob", request, jpuppeteer.cdp.cdp.entity.io.ResolveBlobResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.io.ResolveBlobResponse> asyncResolveBlob(jpuppeteer.cdp.cdp.entity.io.ResolveBlobRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IO.resolveBlob", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.io.ResolveBlobResponse>(future, jpuppeteer.cdp.cdp.entity.io.ResolveBlobResponse.class);
    }
}
package jpuppeteer.api.browser;

import java.util.List;

public interface Interceptor extends Request {

    void abort() throws Exception;

    void continues() throws Exception;

    void respond(int statusCode, List<Header> headers, byte[] body) throws Exception;

    default void respond(int statusCode) throws Exception {
        respond(statusCode, null, null);
    }

}

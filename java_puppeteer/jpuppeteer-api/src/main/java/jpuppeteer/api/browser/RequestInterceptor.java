package jpuppeteer.api.browser;

import java.util.List;

public interface RequestInterceptor extends Interceptor {

    void continues(String method, String url, List<Header> headers, String body) throws Exception;

    default void continues(List<Header> headers) throws Exception {
        continues(null, null, headers, null);
    }

    default void continues(List<Header> headers, String body) throws Exception {
        continues(null, null, headers, body);
    }

    default void continues() throws Exception {
        continues(null, null, null, null);
    }

}

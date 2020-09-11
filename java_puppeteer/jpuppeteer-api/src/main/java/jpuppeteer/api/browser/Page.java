package jpuppeteer.api.browser;

import jpuppeteer.api.constant.MediaType;
import jpuppeteer.api.constant.MouseDefinition;
import jpuppeteer.api.constant.RequestStage;
import jpuppeteer.api.constant.USKeyboardDefinition;

import java.util.List;
import java.util.function.Consumer;

public interface Page extends Frame {

    void authenticate(String username, String password, Consumer<RequestInterceptor> interceptor) throws Exception;

    void authenticate(String username, String password) throws Exception;

    Page opener();

    void setCookies(Cookie... cookies) throws Exception;

    void deleteCookies(String name, String domain, String path, String url) throws Exception;

    default void deleteCookies(String name, String domain) throws Exception {
        deleteCookies(name, domain, null, null);
    }

    void clearCookies() throws Exception;

    List<Cookie> cookies() throws Exception;

    void bringToFront() throws Exception;

    BrowserContext browserContext();

    void close() throws Exception;

    void emulateMedia(MediaType mediaType) throws Exception;

    String evaluateOnNewDocument(String script) throws Exception;

    void removeOnNewDocument(String scriptId) throws Exception;

    void goBack() throws Exception;

    void goForward() throws Exception;

    boolean isClosed();

    /**
     * 重新加载
     * @param force true=disable cache/false=enable cache
     * @throws Exception
     */
    void reload(boolean force) throws Exception;

    default void reload() throws Exception {
        reload(false);
    }

    void setByPassCSP(boolean enable) throws Exception;

    void setCacheEnable(boolean enable) throws Exception;

    void setExtraHTTPHeaders(Header... headers) throws Exception;

    void setGeolocation(double latitude, double longitude, int accuracy) throws Exception;

    <T extends Interceptor> void enableRequestInterception(boolean handleAuthRequest, RequestStage stage, Consumer<T> interceptor, String... urlPatterns) throws Exception;

    default void enableRequestInterception(Consumer<RequestInterceptor> interceptor) throws Exception {
        enableRequestInterception(false, RequestStage.REQUEST, interceptor, "*");
    }

    default void enableRequestInterception(Consumer<RequestInterceptor> interceptor, String... urlPatterns) throws Exception {
        enableRequestInterception(false, RequestStage.REQUEST, interceptor, urlPatterns);
    }

    default void enableResponseInterception(Consumer<Interceptor> interceptor, String... urlPatterns) throws Exception {
        enableRequestInterception(false, RequestStage.RESPONSE, interceptor, urlPatterns);
    }

    default void enableResponseInterception(Consumer<Interceptor> interceptor) throws Exception {
        enableRequestInterception(false, RequestStage.RESPONSE, interceptor, "*");
    }

    void disableRequestInterception() throws Exception;

    void setUserAgent(UserAgent userAgent) throws Exception;

    void setDevice(Device device) throws Exception;

    UserAgent userAgent();

    Device device();


    //mouse event
    void mouseDown(MouseDefinition mouseDefinition) throws Exception;

    default void mouseDown() throws Exception {
        mouseDown(MouseDefinition.LEFT);
    }

    void mouseUp(MouseDefinition mouseDefinition) throws Exception;

    default void mouseUp() throws Exception {
        mouseUp(MouseDefinition.LEFT);
    }

    void mouseMove(double x, double y, int steps) throws Exception;

    default void mouseMove(double x, double y) throws Exception {
        mouseMove(x, y, 1);
    }

    //keyboard event;
    void keyDown(USKeyboardDefinition key) throws Exception;

    void keyUp(USKeyboardDefinition key) throws Exception;

    void press(USKeyboardDefinition key, int delay) throws Exception;

    default void press(USKeyboardDefinition key) throws Exception {
        press(key, 0);
    }

    //touch event
    void touchStart(double x, double y) throws Exception;

    void touchEnd() throws Exception;

    void touchMove(double x, double y) throws Exception;

    void touchCancel() throws Exception;

    /**
     *
     * @return png format byte array
     * @throws Exception
     */
    byte[] screenshot() throws Exception;

    void stopLoading() throws Exception;

}

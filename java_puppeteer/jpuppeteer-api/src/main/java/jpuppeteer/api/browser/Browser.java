package jpuppeteer.api.browser;

import java.net.URI;

public interface Browser {

    void close();


    BrowserContext[] browserContexts();

    /**
     * 等同于创建普通窗口
     * @return
     */
    BrowserContext createContext() throws Exception;

    /**
     * 如果浏览器存在默认的上下文, 则返回默认的上下文, 否则返回null
     * @return
     */
    BrowserContext defaultContext();

    String userAgent() throws Exception;

    String version() throws Exception;

    URI wsEndpoint();

    /**
     * 如果是通过websocket连接的话, 返回null
     * @return
     */
    Process process();

}

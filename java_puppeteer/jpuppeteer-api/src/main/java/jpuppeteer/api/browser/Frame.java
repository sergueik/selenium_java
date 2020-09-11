package jpuppeteer.api.browser;

import com.alibaba.fastjson.TypeReference;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface Frame extends ExecutionContext {

    String frameId();

    Frame parent();

    /**
     * child frames
     * @return
     */
    Frame[] children();

    String content() throws Exception;

    String name();

    void setContent(String content) throws Exception;

    String title() throws Exception;

    URL url() throws Exception;

    void navigate(String url, String referer) throws Exception;

    default void navigate(String url) throws Exception {
        navigate(url, null);
    }

    Element querySelector(String selector) throws Exception;

    List<? extends Element> querySelectorAll(String selector) throws Exception;

    /**
     * 如果返回的对象是node类型的话，会转为Element返回
     * @param expression
     * @param timeout
     * @param unit
     * @param args
     * @return
     * @throws Exception
     */
    BrowserObject wait(String expression, int timeout, TimeUnit unit, Object... args) throws Exception;

    <R> R wait(String expression, int timeout, TimeUnit unit, Class<R> clazz, Object... args) throws Exception;

    <R> R wait(String expression, int timeout, TimeUnit unit, TypeReference<R> type, Object... args) throws Exception;

    Element waitSelector(String selector, int timeout, TimeUnit unit) throws Exception;

    Coordinate scroll(int x, int y) throws Exception;

}


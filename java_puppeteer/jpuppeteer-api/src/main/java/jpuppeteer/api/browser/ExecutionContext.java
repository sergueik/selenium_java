package jpuppeteer.api.browser;

import com.alibaba.fastjson.TypeReference;

public interface ExecutionContext {

    <R> R eval(String expression, Class<R> clazz) throws Exception;

    <R> R eval(String expression, TypeReference<R> type) throws Exception;

    BrowserObject eval(String expression) throws Exception;

    <R> R call(String declaration, Class<R> clazz, Object... args) throws Exception;

    <R> R call(String declaration, TypeReference<R> type, Object... args) throws Exception;

    BrowserObject call(String declaration, Object... args) throws Exception;

}

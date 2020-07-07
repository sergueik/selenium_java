package jpuppeteer.api.browser;

import jpuppeteer.api.constant.ResourceType;

import java.util.List;

public interface Request {

    Frame frame();

    List<Header> headers();

    boolean isNavigationRequest();

    String method();

    String content() throws Exception;

    ResourceType resourceType();

    Response response();

    String url();

}

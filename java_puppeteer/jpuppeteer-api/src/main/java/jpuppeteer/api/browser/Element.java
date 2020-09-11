package jpuppeteer.api.browser;

import jpuppeteer.api.constant.MouseDefinition;

import java.io.File;
import java.util.List;

public interface Element extends BrowserObject {

    Page page();

    Frame frame();

    Element querySelector(String selector) throws Exception;

    List<? extends Element> querySelectorAll(String selector) throws Exception;

    BoundingBox boundingBox() throws Exception;

    boolean isIntersectingViewport() throws Exception;

    BoxModel boxModel() throws Exception;

    void uploadFile(File... files) throws Exception;

    void scrollIntoView() throws Exception;

    //dom event
    void focus() throws Exception;

    void hover() throws Exception;

    //mouse event
    void click(MouseDefinition buttonType, int delay) throws Exception;

    default void click() throws Exception {
        click(MouseDefinition.LEFT, 0);
    }

    //touchscreen event
    void tap(int delay) throws Exception;

    default void tap() throws Exception {
        tap(0);
    }

    void clear() throws Exception;

    //keyboard event
    void input(String text, int delay) throws Exception;

    default void input(String text) throws Exception {
        input(text, 35);
    }

    //select event
    void select(String... values) throws Exception;

    Coordinate scroll(int x, int y) throws Exception;

    String html() throws Exception;

    String text() throws Exception;
}

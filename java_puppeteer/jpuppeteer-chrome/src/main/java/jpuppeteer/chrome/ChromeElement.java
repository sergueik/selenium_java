package jpuppeteer.chrome;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import jpuppeteer.api.browser.BoundingBox;
import jpuppeteer.api.browser.BoxModel;
import jpuppeteer.api.browser.Coordinate;
import jpuppeteer.api.browser.Element;
import jpuppeteer.api.constant.MouseDefinition;
import jpuppeteer.api.constant.USKeyboardDefinition;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectSubtype;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectType;
import jpuppeteer.cdp.cdp.entity.dom.*;
import jpuppeteer.cdp.cdp.entity.input.InsertTextRequest;
import jpuppeteer.cdp.cdp.entity.page.GetLayoutMetricsResponse;
import jpuppeteer.cdp.cdp.entity.runtime.RemoteObject;
import jpuppeteer.chrome.constant.ScriptConstants;
import jpuppeteer.chrome.util.ChromeObjectUtils;
import jpuppeteer.chrome.util.MathUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static jpuppeteer.chrome.ChromeBrowser.DEFAULT_TIMEOUT;

public class ChromeElement extends ChromeBrowserObject implements Element {

    private static final Logger logger = LoggerFactory.getLogger(ChromeElement.class);

    protected ChromePage page;

    protected ChromeFrame frame;

    protected ChromeElement(ChromeFrame frame, RemoteObject object) {
        super(frame.runtime, frame, object);
        this.frame = frame;
        if (frame instanceof ChromePage) {
            this.page = (ChromePage) frame;
        } else {
            ChromeFrame parent = frame.parent;
            while (parent.parent != null) {
                parent = parent.parent;
            }
            this.page = (ChromePage) parent;
        }
    }

    protected ChromeElement(ChromeFrame frame, ChromeBrowserObject object) {
        this(frame, object.object);
    }

    @Override
    public ChromeBrowserObject call(String declaration, Object... args) throws Exception {
        ChromeBrowserObject object = super.call(declaration, args);
        if (RemoteObjectSubtype.NODE.equals(object.subType)) {
            return new ChromeElement(frame, object);
        } else {
            return object;
        }
    }

    @Override
    public ChromeElement querySelector(String selector) throws Exception {
        ChromeBrowserObject object = call("function(selector){return this.querySelector(selector);}", selector);
        if (RemoteObjectType.UNDEFINED.equals(object.type) || RemoteObjectSubtype.NULL.equals(object.subType)) {
            return null;
        }
        return new ChromeElement(frame, object.object);
    }

    @Override
    public List<ChromeElement> querySelectorAll(String selector) throws Exception {
        ChromeBrowserObject browserObject = call("function(selector){return this.querySelectorAll(selector);}", selector);
        List<ChromeBrowserObject> properties = browserObject.getProperties();
        ChromeObjectUtils.releaseObjectQuietly(runtime, browserObject.objectId);
        return properties.stream()
                .map(object -> new ChromeElement(frame, object))
                .collect(Collectors.toList());
    }

    @Override
    public ChromePage page() {
        return page;
    }

    @Override
    public ChromeFrame frame() {
        return frame;
    }

    @Override
    public boolean isIntersectingViewport() throws Exception {
        return call(ScriptConstants.ELEMENT_IS_INTERSECTING_VIEWPORT, Boolean.class);
    }

    @Override
    public BoundingBox boundingBox() throws Exception {
        BoxModel model = boxModel();
        if (model == null) {
            return null;
        }

        Coordinate[] border = model.getBorder();

        double x = MathUtils.min(border[0].getX(), border[1].getX(), border[2].getX(), border[3].getX());
        double y = MathUtils.min(border[0].getY(), border[1].getY(), border[2].getY(), border[3].getY());

        double width = MathUtils.max(border[0].getX(), border[1].getY(), border[2].getX(), border[3].getX()) - x;
        double height = MathUtils.max(border[0].getY(), border[1].getY(), border[2].getY(), border[3].getY()) - y;
        return new BoundingBox(x, y, width, height);
    }

    @Override
    public BoxModel boxModel() throws Exception {
        GetBoxModelRequest request = new GetBoxModelRequest();
        request.setObjectId(objectId);
        GetBoxModelResponse resp = frame.dom.getBoxModel(request, DEFAULT_TIMEOUT);
        if (resp == null) {
            return null;
        }
        return new BoxModel(
                resp.getModel().getWidth(),
                resp.getModel().getHeight(),
                toCoordinates(resp.getModel().getContent()),
                toCoordinates(resp.getModel().getPadding()),
                toCoordinates(resp.getModel().getBorder()),
                toCoordinates(resp.getModel().getMargin())
        );
    }

    private List<Coordinate> toCoordinates(List<Double> items) {
        List<Coordinate> coordinates = new ArrayList<>(items.size() / 2);
        for(int i=0; i<items.size();) {
            coordinates.add(new Coordinate(items.get(i++), items.get(i++)));
        }
        return coordinates;
    }

    @Override
    public void uploadFile(File... files) throws Exception {
        List<String> names = Arrays.stream(files).map(file -> file.getAbsolutePath()).collect(Collectors.toList());
        SetFileInputFilesRequest request = new SetFileInputFilesRequest();
        request.setFiles(names);
        request.setObjectId(objectId);
        frame.dom.setFileInputFiles(request, DEFAULT_TIMEOUT);
    }

    @Override
    public void focus() throws Exception {
        FocusRequest request = new FocusRequest();
        request.setObjectId(objectId);
        frame.dom.focus(request, DEFAULT_TIMEOUT);
    }

    @Override
    public void hover() throws Exception {
        Coordinate center = clickablePoint();
        page.mouseMove(center.getX(), center.getY(), 1);
    }

    @Override
    public void scrollIntoView() throws Exception {
        call(ScriptConstants.ELEMENT_SCROLL_INTO_VIEW);
    }

    private void insertText(String text) throws Exception {
        InsertTextRequest request = new InsertTextRequest();
        request.setText(text);
        frame.input.insertText(request, DEFAULT_TIMEOUT);
    }

    public Coordinate clickablePoint() throws Exception {
        GetContentQuadsRequest contentQuadsRequest = new GetContentQuadsRequest();
        contentQuadsRequest.setObjectId(objectId);
        GetContentQuadsResponse contentQuads = frame.dom.getContentQuads(contentQuadsRequest, DEFAULT_TIMEOUT);
        GetLayoutMetricsResponse layout = frame.page.getLayoutMetrics(DEFAULT_TIMEOUT);
        if (contentQuads == null || CollectionUtils.isEmpty(contentQuads.getQuads())) {
            throw new Exception("Node is either not visible or not an HTMLElement");
        }
        int clientWidth = layout.getLayoutViewport().getClientWidth();
        int clientHeight = layout.getLayoutViewport().getClientHeight();
        List<List<Coordinate>> quads = contentQuads.getQuads().stream()
                .map(quad -> Lists.newArrayList(
                        new Coordinate(quad.get(0), quad.get(1)),
                        new Coordinate(quad.get(2), quad.get(3)),
                        new Coordinate(quad.get(4), quad.get(5)),
                        new Coordinate(quad.get(6), quad.get(7))
                ))
                .map(coordinates -> coordinates.stream().map(coordinate -> new Coordinate(
                        Math.min(Math.max(coordinate.getX(), 0), clientWidth),
                        Math.min(Math.max(coordinate.getY(), 0), clientHeight)
                )).collect(Collectors.toList()))
                .filter(coordinates -> {
                    int area = 0;
                    for (int i = 0; i < coordinates.size(); ++i) {
                        Coordinate p1 = coordinates.get(i);
                        Coordinate p2 = coordinates.get((i + 1) % coordinates.size());
                        area += (p1.getX() * p2.getY() - p2.getX() * p1.getY()) / 2;
                    }
                    return Math.abs(area) > 1;
                })
                .collect(Collectors.toList());
        if (quads == null || CollectionUtils.isEmpty(quads)) {
            throw new Exception("Node is either not visible or not an HTMLElement");
        }
        List<Coordinate> quad = quads.get(0);
        int x = 0;
        int y = 0;
        for(Coordinate coord : quad) {
            x += coord.getX();
            y += coord.getY();
        }
        return new Coordinate(x / 4, y / 4);
    }

    @Override
    public void click(MouseDefinition mouseDefinition, int delay) throws Exception {
        scrollIntoView();
        hover();
        page.mouseDown(mouseDefinition);
        if (delay > 0) {
            TimeUnit.MILLISECONDS.sleep(delay);
        }
        page.mouseUp(mouseDefinition);
    }

    @Override
    public void tap(int delay) throws Exception {
        scrollIntoView();
        Coordinate center = clickablePoint();
        page.touchStart(center.getX(), center.getY());
        if (delay > 0) {
            TimeUnit.MILLISECONDS.sleep(delay);
        }
        page.touchEnd();
    }

    @Override
    public void clear() throws Exception {
        call("function(){this.value='';}");
    }

    @Override
    public void input(String text, int delay) throws Exception {
        //先将输入框聚焦才能输入内容
        focus();
        for(char chr : text.toCharArray()) {
            String chrStr = String.valueOf(chr);
            USKeyboardDefinition def = USKeyboardDefinition.find(chrStr);
            if (def != null) {
                page.press(def, 0);
            } else {
                insertText(chrStr);
            }
            if (delay > 0) {
                TimeUnit.MILLISECONDS.sleep(delay);
            }
        }
    }

    @Override
    public void select(String... values) throws Exception {
        call(ScriptConstants.ELEMENT_SELECT, (Object[]) values);
    }

    @Override
    public Coordinate scroll(int x, int y) throws Exception {
        JSONObject offset = call(ScriptConstants.SCROLL, JSONObject.class, x, y);
        return new Coordinate(offset.getDouble("scrollX"), offset.getDouble("scrollY"));
    }

    @Override
    public String html() throws Exception {
        return call("function(){this.innerHTML}", String.class);
    }

    @Override
    public String text() throws Exception {
        return call("function(){this.innerText}", String.class);
    }
}

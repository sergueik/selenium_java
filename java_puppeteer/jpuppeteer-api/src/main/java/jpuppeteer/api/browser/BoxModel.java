package jpuppeteer.api.browser;

import java.util.List;

public class BoxModel {

    private final int width;

    private final int height;

    private final List<Coordinate> content;

    private final List<Coordinate> padding;

    private final List<Coordinate> border;

    private final List<Coordinate> margin;

    public BoxModel(int width, int height, List<Coordinate> content, List<Coordinate> padding, List<Coordinate> border, List<Coordinate> margin) {
        this.width = width;
        this.height = height;
        this.content = content;
        this.padding = padding;
        this.border = border;
        this.margin = margin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Coordinate[] getContent() {
        return toArray(content);
    }

    public Coordinate[] getPadding() {
        return toArray(padding);
    }

    public Coordinate[] getBorder() {
        return toArray(border);
    }

    public Coordinate[] getMargin() {
        return toArray(margin);
    }

    private static final Coordinate[] toArray(List<Coordinate> coordinateList) {
        if (coordinateList == null || coordinateList.size() == 0) {
            return new Coordinate[0];
        }
        Coordinate[] coordinates = new Coordinate[coordinateList.size()];
        return coordinateList.toArray(coordinates);
    }
}

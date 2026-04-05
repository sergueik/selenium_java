package io.github.ashwithpoojary98;

public class Rectangle {
    public final int x;
    public final int y;
    public final int height;
    public final int width;

    public Rectangle(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Rectangle(Point p, Dimension d) {
        this.x = p.x;
        this.y = p.y;
        this.height = d.height;
        this.width = d.width;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public Dimension getDimension() {
        return new Dimension(width, height);
    }
}

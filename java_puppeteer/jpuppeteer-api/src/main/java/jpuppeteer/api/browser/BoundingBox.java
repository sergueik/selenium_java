package jpuppeteer.api.browser;

public class BoundingBox extends Coordinate {

	private double width;

	private double height;

	public BoundingBox(double x, double y, double width, double height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}

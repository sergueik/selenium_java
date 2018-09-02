package screen;

import screen.measurement.Point;
import javafx.geometry.Rectangle2D;

public class Screen {
	@SuppressWarnings({ "restriction", "restriction" })
	private static final Rectangle2D bounds = javafx.stage.Screen.getPrimary()
			.getVisualBounds();

	public static Point calculateCenter(double width, double height) {
		@SuppressWarnings("restriction")
		double x = (bounds.getWidth() - width) / 2;
		@SuppressWarnings("restriction")
		double y = (bounds.getHeight() - height) / 2;
		return new Point(x, y);

	}
}

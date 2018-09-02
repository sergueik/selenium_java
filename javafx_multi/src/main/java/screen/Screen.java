package screen;

import screen.measurement.Point;
import javafx.geometry.Rectangle2D;

@SuppressWarnings({ "restriction" })
public class Screen {

	private static final Rectangle2D bounds = javafx.stage.Screen.getPrimary()
			.getVisualBounds();

	public static Point calculateCenter(double width, double height) {
		return new Point((bounds.getWidth() - width) / 2,
				(bounds.getHeight() - height) / 2);

	}
}

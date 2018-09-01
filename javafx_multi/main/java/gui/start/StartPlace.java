package gui.start;

import gui.PlaceHelper;
import gui.ViewHelper;
import javafx.stage.Stage;
import screen.measurement.Scale;

public class StartPlace extends PlaceHelper {
	@Override
	public void start(Stage primaryStage) {
		super.stage = primaryStage;

		initWindow("Start this stage, let's do it!", Scale.PLACE_START,
				ViewHelper.START);
	}
}

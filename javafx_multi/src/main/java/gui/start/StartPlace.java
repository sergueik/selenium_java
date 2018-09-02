package gui.start;

import gui.PlaceHelper;
import gui.ViewHelper;
import javafx.stage.Stage;
import screen.measurement.Scale;
import java.lang.IllegalStateException;

@SuppressWarnings("restriction")
public class StartPlace extends PlaceHelper {
	@Override
	public void start(Stage primaryStage) {
		super.stage = primaryStage;

		/*
		 NOTE: cannot move the files to arbitrary 
		 resources directory 
		 
		 java.lang.IllegalStateException: Cannot load file Start.fxml
		    at 
		    com.airhacks.afterburner.views.FXMLView.getResourceCamelOrLowerCase(FXMLView.java:221)
		    at com.airhacks.afterburner.views.FXMLView.getFXMLName(FXMLView.java:205
		*/

		try {
			initWindow("Start this stage, let's do it!", "gui/start/Start.fxml",
					Scale.PLACE_START, ViewHelper.PLACE_START);
		} catch (Exception e) {
			System.err.println("Exception (ignored) " + e.toString());
			initWindow("Start this stage, let's do it!", Scale.PLACE_START,
					ViewHelper.PLACE_START);
		}
	}
}

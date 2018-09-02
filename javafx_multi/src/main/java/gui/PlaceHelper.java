package gui;

import screen.measurement.Point;

import gui.start.StartPresenter;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import screen.Screen;
import screen.measurement.Scale;
import javafx.fxml.FXMLLoader;

@SuppressWarnings("restriction")
public abstract class PlaceHelper extends Application {
	protected Stage stage;

	protected void initWindow(String title, String view, Scale scale,
			ViewHelper viewHelper) {
		// not reached
		System.err.println("Initwindow (1)");
		FXMLLoader loader = new FXMLLoader();
		// ClassLoader classLoader = PlaceHelper.class.getClassLoader();
		// loader.setLocation(classLoader.getResource(view));
		loader.setLocation(getClass().getResource(view));

		stage.setTitle(title);

		if (!scale.equals(Scale.PLACE_START))
			stage.initModality(Modality.APPLICATION_MODAL);

		setSize(scale);
		setPosition(scale);

		Scene scene = initScene(viewHelper);
		initFrame(viewHelper);
		stage.setScene(scene);
		stage.show();
	}

	@SuppressWarnings({ "restriction" })
	protected void initWindow(String title, Scale scale, ViewHelper viewHelper) {
		System.err.println("Initwindow (2)");

		stage.setTitle(title);

		if (!scale.equals(Scale.PLACE_START))
			stage.initModality(Modality.APPLICATION_MODAL);

		setSize(scale);
		setPosition(scale);

		Scene scene = initScene(viewHelper);
		initFrame(viewHelper);
		stage.setScene(scene);
		stage.show();
	}

	@SuppressWarnings("restriction")
	private void setSize(Scale scale) {
		stage.setWidth(scale.width);
		stage.setHeight(scale.height);
	}

	private void setPosition(Scale scale) {
		Point point = Screen.calculateCenter(scale.width, scale.height);

		stage.setX(point.x);
		stage.setY(point.y);
	}

	private Scene initScene(ViewHelper viewAction) {
		Parent view = viewAction.view.getView();

		return view.isNeedsLayout() ? new Scene(view) : view.getScene();
	}

	private void initFrame(ViewHelper helper) {
		if (helper.view.getPresenter() instanceof StartPresenter)
			((StartPresenter) helper.view.getPresenter()).label.setText("initFrame");
	}

	@Override
	public void start(@SuppressWarnings("restriction") Stage primaryStage) {
		throw new UnsupportedOperationException();
	}
}

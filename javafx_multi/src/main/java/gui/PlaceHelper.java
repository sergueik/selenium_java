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

public abstract class PlaceHelper extends Application {
	@SuppressWarnings("restriction")
	protected Stage stage;

	@SuppressWarnings("restriction")
	protected void initWindow(String title, Scale scale, ViewHelper viewHelper) {
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

	@SuppressWarnings("restriction")
	private void setPosition(Scale scale) {
		Point point = Screen.calculateCenter(scale.width, scale.height);

		stage.setX(point.x);
		stage.setY(point.y);
	}

	/**
	 * Check if Scene is ready.
	 *
	 * @param viewAction
	 * @return
	 */
	@SuppressWarnings("restriction")
	private Scene initScene(ViewHelper viewAction) {
		Parent view = viewAction.view.getView();

		return view.isNeedsLayout() ? new Scene(view) : view.getScene();
	}

	/**
	 * Override this method, to add any additional actions when you want to open new Scene.
	 * Such as clear fields or something else.
	 *
	 * @param helper
	 */
	@SuppressWarnings("restriction")
	private void initFrame(ViewHelper helper) {
		if (helper.view.getPresenter() instanceof StartPresenter)
			((StartPresenter) helper.view.getPresenter()).label.setText("initFrame");
	}

	@Override
	public void start(@SuppressWarnings("restriction") Stage primaryStage) {
		throw new UnsupportedOperationException();
	}
}

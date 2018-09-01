package gui;

import gui.start.StartPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.var;
import screen.Screen;
import screen.measurement.Scale;

public abstract class PlaceHelper extends Application {
	protected Stage stage;

	protected void initWindow(String title, Scale scale, ViewHelper viewHelper) {
		stage.setTitle(title);

		if (!scale.equals(Scale.PLACE_START))
			stage.initModality(Modality.APPLICATION_MODAL);

		setSize(scale);
		setPosition(scale);

		var scene = initScene(viewHelper);
		initFrame(viewHelper);
		stage.setScene(scene);
		stage.show();
	}

	private void setSize(Scale scale) {
		stage.setWidth(scale.width);
		stage.setHeight(scale.height);
	}

	private void setPosition(Scale scale) {
		var point = Screen.calculateCenter(scale.width, scale.height);

		stage.setX(point.x);
		stage.setY(point.y);
	}

	/**
	 * Check if Scene is ready.
	 *
	 * @param viewAction
	 * @return
	 */
	private Scene initScene(ViewHelper viewAction) {
		var view = viewAction.view.getView();

		return view.isNeedsLayout() ? new Scene(view) : view.getScene();
	}

	/**
	 * Override this method, to add any additional actions when you want to open new Scene.
	 * Such as clear fields or something else.
	 *
	 * @param helper
	 */
	private void initFrame(ViewHelper helper) {
		if (helper.view.getPresenter() instanceof StartPresenter)
			((StartPresenter) helper.view.getPresenter()).label.setText("initFrame");
	}

	@Override
	public void start(Stage primaryStage) {
		throw new UnsupportedOperationException();
	}
}

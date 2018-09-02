package gui;

import com.airhacks.afterburner.views.FXMLView;
import gui.child.ChildView;
import gui.start.StartView;

public enum ViewHelper {
	PLACE_START(new StartView()), PLACE_CHILD(new ChildView());

	public FXMLView view;

	ViewHelper(FXMLView view) {
		this.view = view;
	}
}

package gui;

import com.airhacks.afterburner.views.FXMLView;
import gui.child.ChildView;
import gui.start.StartView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ViewHelper {
	START(new StartView()), 
	CHILD(new ChildView());

	public FXMLView view;
}

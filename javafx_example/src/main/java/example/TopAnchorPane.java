package example;

import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;

import javafx.scene.Node;
import javafx.stage.Window;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.scene.layout.AnchorPane;

public class TopAnchorPane extends AnchorPane {
	// https://stackoverflow.com/questions/19785130/javafx-event-when-objects-are-rendered
	// need to extend AnchorPane and override
	private boolean isRendered = false;

	protected void layoutChildren() {
		{
			super.layoutChildren();
			if (!isRendered) {
				// This is the first time the node is rendered. Event trigger logic
				// should be here
			}
			isRendered = true;
		}
	}

}

package com.github.sergueik.swet_javafx;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.Node;
import javafx.stage.Window;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

@SuppressWarnings({ "unused", "restriction" })
public class VanillaControllerEx {

	private Stage mainStage;

	@FXML
	private Button continueButton;

	@FXML
	private Label headerText;
	
	@FXML
	private Label contentHeader;

	@FXML
	private TextField filterField;

	private Parent fxmlEdit;
	private FXMLLoader fxmlLoader = new FXMLLoader();

	private Stage stage;
	private Stage changestage;

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	@FXML
	private void initialize() {

	}
}

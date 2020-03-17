package com.github.sergueik.swet_javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import impl.org.controlsfx.i18n.SimpleLocalizedStringProperty;
// import application.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

@SuppressWarnings("restriction")

public class TableViewDynamic extends Application {
	@FXML
	public TableView<ObservableList<String>> tableView;

	public void initialize(URL location, ResourceBundle resources) {
		List<String> head = new ArrayList<>(Arrays.asList("first", "two", "three"));
		List<String> row0 = new ArrayList<>(Arrays.asList("A", "b", "123"));
		List<String> row1 = new ArrayList<>(Arrays.asList("C", "d", "456"));

		initHead(head);

		addRow(row0);
		addRow(row1);
	}

	private void initHead(List<String> head) {
		for (int i = 0; i < head.size(); i++) {
			int finalI = i;
			TableColumn<ObservableList<String>, String> col = new TableColumn<>(
					head.get(i));
			col.setCellValueFactory(param -> new SimpleLocalizedStringProperty(
					param.getValue().get(finalI)));

			tableView.getColumns().add(col);
		}
	}

	private void addRow(List<String> row) {
		ObservableList<String> a = FXCollections.observableArrayList();
		a.addAll(row);

		tableView.getItems().add(a);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}

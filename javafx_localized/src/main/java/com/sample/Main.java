package com.sample;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Localization Entry Sample. origin:
 * https://github.com/tomoTaka01/LocalizationSample
 */
@SuppressWarnings("restriction")
public class Main extends Application {
	private BorderPane root;
	private EntryStartController entryStartController;
	private HBox bottomHBox;
	private Button startButton;
	private Button resetLocaleButton;
	private Button nextButton;
	private PresentInfo presentInfo;
	private int fxmlIx = 0;
	private ENTRY[] fxmls = ENTRY.values();

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.presentInfo = new PresentInfo();
		Locale.setDefault(Locale.US);
		this.root = new BorderPane();
		setEntryStart();
		this.bottomHBox = new HBox();
		this.bottomHBox.getStyleClass().add("hbox");
		this.bottomHBox.setId("hbox-custom");
		this.startButton = new Button("Start");
		this.resetLocaleButton = new Button("reset locale");
		this.nextButton = new Button("next");
		this.bottomHBox.getChildren().add(this.startButton);

		this.startButton.setOnAction(event -> {
			this.presentInfo.localizationInfo = this.entryStartController.getLocalizationInfo();
			this.root.getChildren().remove(this.presentInfo.centerNode);
			this.presentInfo.topTitle = new LocalizationInfoTitle(this.presentInfo.localizationInfo);
			this.root.setTop(this.presentInfo.topTitle);
			this.bottomHBox.getChildren().remove(this.startButton);
			this.bottomHBox.getChildren().addAll(this.resetLocaleButton, this.nextButton);
			setEntry();
		});
		this.resetLocaleButton.setOnAction(event -> {
			this.root.getChildren().remove(this.presentInfo.centerNode);
			this.root.getChildren().remove(this.presentInfo.topTitle);
			fxmlIx = 0;
			this.bottomHBox.getChildren().remove(this.resetLocaleButton);
			this.bottomHBox.getChildren().remove(this.nextButton);
			this.bottomHBox.getChildren().addAll(this.startButton);
			this.setEntryStart();
		});
		this.nextButton.setOnAction(event -> {
			this.root.getChildren().remove(this.presentInfo.centerNode);
			fxmlIx++;
			setEntry();
		});
		this.root.setBottom(this.bottomHBox);
		Scene scene = new Scene(root, 700, 500);
		stage.setTitle("Localization Sample");
		scene.getStylesheets().addAll("/css/layout.css");
		stage.setScene(scene);
		stage.show();
	}

	private void setEntryStart() {
		URL url = getClass().getResource("/EntryStart.fxml");
		FXMLLoader loader = new FXMLLoader(url);
		try {
			this.presentInfo.centerNode = loader.<Node>load();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.root.setCenter(this.presentInfo.centerNode);
		this.entryStartController = loader.getController();
	}

	private void setEntry() {
		ENTRY entry = fxmls[fxmlIx];
		EntryBase entryBase = entry.getController();
		entryBase.loadFxml(this.presentInfo.localizationInfo, entry.getFxmlPath());
		this.root.setCenter(entryBase);
	}

	private class PresentInfo {
		Node centerNode;
		LocalizationInfo localizationInfo;
		LocalizationInfoTitle topTitle;
	}

}

enum ENTRY {
	Entry1("/Entry1.fxml") {
		@Override
		EntryBase getController() {
			return new Entry1();
		}
	},
	Entry2("/Entry2.fxml") {
		@Override
		EntryBase getController() {
			return new Entry2();
		}
	};
	private String fxmlPath;

	private ENTRY(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}

	abstract EntryBase getController();

	String getFxmlPath() {
		return this.fxmlPath;
	}
}

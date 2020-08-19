package com.sample;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger log = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		presentInfo = new PresentInfo();
		Locale.setDefault(Locale.US);
		root = new BorderPane();
		setEntryStart();
		bottomHBox = new HBox();
		bottomHBox.getStyleClass().add("hbox");
		bottomHBox.setId("hbox-custom");
		startButton = new Button("Start");
		resetLocaleButton = new Button("reset locale");
		nextButton = new Button("next");
		bottomHBox.getChildren().add(this.startButton);

		startButton.setOnAction(event -> {
			presentInfo.localizationInfo = this.entryStartController.getLocalizationInfo();
			root.getChildren().remove(this.presentInfo.centerNode);
			presentInfo.topTitle = new LocalizationInfoTitle(this.presentInfo.localizationInfo);
			root.setTop(this.presentInfo.topTitle);
			bottomHBox.getChildren().remove(this.startButton);
			bottomHBox.getChildren().addAll(this.resetLocaleButton, this.nextButton);
			setEntry();
		});
		resetLocaleButton.setOnAction(event -> {
			root.getChildren().remove(presentInfo.centerNode);
			root.getChildren().remove(presentInfo.topTitle);
			fxmlIx = 0;
			bottomHBox.getChildren().remove(resetLocaleButton);
			bottomHBox.getChildren().remove(nextButton);
			bottomHBox.getChildren().addAll(startButton);
			setEntryStart();
		});
		nextButton.setOnAction(event -> {
			root.getChildren().remove(presentInfo.centerNode);
			fxmlIx++;
			setEntry();
		});
		root.setBottom(bottomHBox);
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
			presentInfo.centerNode = loader.<Node>load();
		} catch (IOException e) {
			log.info("Exception (ignored) : " + e.toString());
			// Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
		}

		root.setCenter(this.presentInfo.centerNode);
		entryStartController = loader.getController();
	}

	private void setEntry() {
		ENTRY entry = fxmls[fxmlIx];
		EntryBase entryBase = entry.getController();
		entryBase.loadFxml(presentInfo.localizationInfo, entry.getFxmlPath());
		root.setCenter(entryBase);
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

package com.github.sergueik.swet_javafx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class LabelFontEx extends Application {
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setWidth(240);
		stage.setHeight(90);

		HBox hbox = new HBox();

		Properties properties = new Properties();
		try {
			String propertiesFileName = "fontstyle.properties";
			FileInputStream in = new FileInputStream(
					String.format("%s/src/main/resources/%s",
							System.getProperty("user.dir"), propertiesFileName));
			properties.load(in);
			in.close();
		} catch (IOException e) {
			System.err.println("Exception : " + e.toString());
		}

		Label label = new Label("Example");
		// fallback to system default
		label.setFont(Font.font("System", FontWeight.NORMAL, (double) 30.0));
		String style = properties.getProperty("font");
		if (style != null) {
			// https://www.programcreek.com/java-api-examples/?class=javafx.scene.control.Label&method=setStyle
			// https://www.programcreek.com/java-api-examples/?api=javafx.scene.text.FontWeight
			System.err.println("Applying style : " + style);
			label.setStyle(style);
			System.err
					.println("Label font information: " + label.getFont().toString());
			// will not change
			// Label font information: Font[name=System Regular, family=System,
			// style=Regular, size=30.0]
			System.err.println("Style information: " + label.getStyle().toString());
			// Style information: -fx-font-size:36px;-fx-text-fill:
			// blue;-fx-font-family: "Comic Sans MS";
		}
		hbox.setSpacing(10);
		hbox.getChildren().add((label));
		((Group) scene.getRoot()).getChildren().add(hbox);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

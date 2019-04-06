package com.github.sergueik.swet_javafx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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

	private final Properties properties = new Properties();
	private String propertiesFileName = "fontstyle.properties";
	private String propertiesDir = getPropertyEnv("TEST_PROPERTIES_DIR",
			String.format("%s/src/main/resources", System.getProperty("user.dir")));
	private String propertiesFilePath = String.format("%s/%s", propertiesDir,
			propertiesFileName);

	@Override
	public void start(Stage stage) {

		Scene scene = new Scene(new Group());
		stage.setWidth(240);
		stage.setHeight(90);

		HBox hbox = new HBox();

		try {
			FileInputStream in = new FileInputStream(propertiesFilePath);
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
			// NOTE: font will not change after applying the style
			System.err
					.println("Label font information: " + label.getFont().toString());
			// fully define from font information:
			// see: https://toster.ru/q/619327?e=7468219#comment_1874221
			Font labelFont = label.getFont();
			System.err.println("Generated style information: " + String.format(
					"-fx-font-size: %2s; -fx-font-family: \"%2s\"; -fx-text-fill: #%2s;",
					labelFont.getSize(), labelFont.getName(),
					label.getTextFill().toString().substring(2)));
			// Update the style dynamically into the same properties file.
			String labelFontStyle = String.format("%s -fx-text-fill: purple;",
					label.getStyle().toString());
			// Let's add some style!
			System.err.println("Label style information: " + labelFontStyle);
			try {
				properties.setProperty("font", labelFontStyle);
				properties.store(new FileOutputStream(propertiesFilePath), null);
			} catch (IOException e) {
				System.err.println("Exception : " + e.toString());
			}
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

	// origin:
	// https://github.com/TsvetomirSlavov/wdci/blob/master/code/src/main/java/com/seleniumsimplified/webdriver/manager/EnvironmentPropertyReader.java
	public static String getPropertyEnv(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
			if (value == null) {
				value = defaultValue;
			}
		}
		return value;
	}
}

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
	private String propertiesFilePath = getPropertyEnv("TEST_PROPERTIES_PATH",
			String.format("%s/src/main/resources", System.getProperty("user.dir")));

	@Override
	public void start(Stage stage) {

		Scene scene = new Scene(new Group());
		stage.setWidth(240);
		stage.setHeight(90);

		HBox hbox = new HBox();

		try {
			FileInputStream in = new FileInputStream(
					String.format("%s/%s", propertiesFilePath, propertiesFileName));
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
			// NOTE: font will not change
			System.err
					.println("Label font information: " + label.getFont().toString());
			// Update the style dynamically into the same properties file.
			String labelFontStyle = String.format("%s -fx-text-fill: purple;",
					label.getStyle().toString());
			// Let's add some style!
			System.err.println("Style information: " + labelFontStyle);
			properties.setProperty("font", labelFontStyle);
			try {
				properties.store(
						new FileOutputStream(
								String.format("%s/%s", propertiesFilePath, propertiesFileName)),
						null);
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

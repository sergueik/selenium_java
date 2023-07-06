package example;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.fxmisc.cssfx.CSSFX;

@SuppressWarnings("restriction")
public class Example extends Application {

	private String mdfxTxt = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		CSSFX.start();

		mdfxTxt = IOUtils.toString(getClass().getResourceAsStream("/syntax.md"),
				"UTF-8");
		mdfxTxt = mdfxTxt.replaceAll("local_image.jpg",
				getResourcePath("local_image.jpg"));
		System.err.println("Sample: " + mdfxTxt);
		MarkdownView markdownView = new MarkdownView(mdfxTxt) {
			// @Override
			// public boolean showChapter(int[] currentChapter) {
			// return currentChapter[1] == 1;
			// }

			@Override
			public void setLink(Node node, String link, String description) {
				System.out.println("setLink: " + link);
				node.setCursor(Cursor.HAND);
				node.setOnMouseClicked(e -> {
					System.out.println("link: " + link);
				});
			}

			@Override
			public Node generateImage(String url) {
				if (url.equals("node://colorpicker")) {
					return new ColorPicker();
				} else {
					return super.generateImage(url);
				}
			}
		};

		TextArea textArea = new TextArea(mdfxTxt);

		markdownView.mdStringProperty().bind(textArea.textProperty());
		markdownView.getStylesheets().add("/css/mdfx-sample.css");

		ScrollPane content = new ScrollPane(markdownView);

		content.setFitToWidth(true);

		textArea.setMinWidth(350);
		HBox root = new HBox(textArea, content);

		Scene scene = new Scene(root, 700, 700);

		primaryStage.setScene(scene);

		primaryStage.show();
	}

	private static boolean debug = true;

	// NOTE: getResourceURI may not work with standalone or web hosted
	// application
	public String getResourceURI(String resourceFileName) {

		if (debug) {
			System.err.println("Getting resource URI for: " + resourceFileName);
		}
		Class<?> thisClass = this.getClass();
		System.err.println("Class: " + thisClass.getSimpleName());
		ClassLoader classLoader = thisClass.getClassLoader();
		URL resourceURL = classLoader.getResource(resourceFileName);
		if (resourceURL != null) {
			try {
				System.err.println("Resource URL: " + resourceURL.toString());
				URI uri = resourceURL.toURI();
				if (debug) {
					System.err.println("Resource URI: " + uri.toString());
				}
				return uri.toString();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}

	public static String getResourcePath(String resourceFileName) {
		String prefix = "file://";
		final String resourcePath = String
				.format("%s/%s/src/main/resources/%s", prefix,
						System.getProperty("user.dir"), resourceFileName)
				.replaceAll("\\\\", "/");
		if (debug)
			System.err.println("Project based resource path: " + resourcePath);

		return resourcePath;
	}

}
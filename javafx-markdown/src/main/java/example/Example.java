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
import org.apache.commons.io.IOUtils;
import org.fxmisc.cssfx.CSSFX;

@SuppressWarnings("restriction")
public class Example extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		CSSFX.start();

		String mdfxTxt = IOUtils
				.toString(getClass().getResourceAsStream("/syntax.md"), "UTF-8");

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
}
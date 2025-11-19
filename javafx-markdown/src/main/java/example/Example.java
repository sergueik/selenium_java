package example;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import org.fxmisc.cssfx.CSSFX;

@SuppressWarnings("restriction")
public class Example extends Application {

    private static boolean debug = true;
    private String mdfxTxt = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        CSSFX.start();

        // Load markdown text and replace local image paths
        mdfxTxt = IOUtils.toString(getClass().getResourceAsStream("/syntax.md"), "UTF-8");
        mdfxTxt = mdfxTxt.replaceAll("local_image.jpg", getResourcePath("local_image.jpg"));
        if (debug) System.err.println("Sample: " + mdfxTxt);

        MarkdownView markdownView = new MarkdownView(mdfxTxt) {

            @Override
            public void setLink(Node node, String link, String description) {
                if (debug) System.out.println("setLink: " + link);
                node.setCursor(Cursor.HAND);
                node.setOnMouseClicked(e -> System.out.println("link: " + link));
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

        // --- Top content: TextArea + MarkdownView ---
        HBox mainContent = new HBox(textArea, content);
        mainContent.setSpacing(5);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // InputStream propStream = getClass().getResourceAsStream("/application.properties");
        
        Properties properties = new Properties();
        
        try (InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input != null) {
            	properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String version = properties.getProperty("version", "0.1.0");

        Label versionLabel = new Label(String.format("Version: %s", version));
        versionLabel.setFont(Font.font("Arial", 12));
        versionLabel.setTextFill(Color.BLACK);
        versionLabel.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-padding: 2;");

        HBox bottomBox = new HBox(versionLabel);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(5));
        bottomBox.setStyle("-fx-background-color: #f0f0f0;");

        // --- Root VBox ---
        VBox root = new VBox(mainContent, bottomBox);
        root.setSpacing(2);

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Resource path helper ---
    public static String getResourcePath(String resourceFileName) {
        String prefix = "file://";
        final String resourcePath = String
                .format("%s/%s/src/main/resources/%s", prefix, System.getProperty("user.dir"), resourceFileName)
                .replaceAll("\\\\", "/");
        if (debug) System.err.println("Project based resource path: " + resourcePath);
        return resourcePath;
    }

}

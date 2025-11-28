package example;

import javafx.application.Application;
import javafx.application.Platform; 
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;

import javafx.scene.web.WebView;

import javafx.geometry.Orientation;
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
import java.util.Set;

import org.apache.commons.io.IOUtils;

import org.fxmisc.cssfx.CSSFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class Example extends Application {
	private final Logger logger = LoggerFactory.getLogger(Example.class);

	private static boolean debug = true;
	private String mdfxTxt = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		CSSFX.start();

		// Load markdown text and replace local image paths
		mdfxTxt = IOUtils.toString(getClass().getResourceAsStream("/syntax.md"), "UTF-8");
		mdfxTxt = mdfxTxt.replaceAll("local_image.jpg", getResourcePath("local_image.jpg"));

		MarkdownView markdownView = new MarkdownView(mdfxTxt) {

			@Override
			public void setLink(Node node, String link, String description) {
		        logger.info("setLink: " + link);
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

		// InputStream propStream =
		// getClass().getResourceAsStream("/application.properties");

		Properties properties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("application.properties")) {
			if (input != null) {
				properties.load(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String version = properties.getProperty("version", "0.1.0");

		Button btnSync = new Button("Sync Scroll");

		btnSync.setOnAction(e -> {
		    ScrollBar vbar = findVerticalScrollbar(textArea);
		    if (vbar == null) return;
		    final double pct = vbar.getValue();

		    final WebView webView = markdownView.getWebView();
			logger.info(String.format("webWiew: %s", (webView != null ? webView.toString() : "null")));
		    logger.info(String.format("Scroll %.2f", pct));

		    if (webView == null) return;

		    Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		        	// https://docs.oracle.com/javase/8/javafx/api/javafx/concurrent/Worker.html
		            final Worker<Void> loadWorker = webView.getEngine().getLoadWorker();
		            
		            
		            logger.info(String.format("btnSync clicked, WebView = " + webView));
		            logger.info(String.format("WebEngine = " + webView.getEngine()));
		            logger.info(String.format("LoadWorker state = " + webView.getEngine().getLoadWorker().getState()));
		            
		            Runnable doScroll = new Runnable() {
		                @Override
		                public void run() {
		                    String js = "var root = document.documentElement; var h = root.scrollHeight - root.clientHeight; root.scrollTop = h * " + pct + ";";
		                    
		                    js =
		                    	    "var pct = " + pct + ";" +
		                    	    "var de = document.documentElement;" +
		                    	    "var b = document.body;" +
		                    	    "var h1 = de.scrollHeight - de.clientHeight;" +
		                    	    "var h2 = b.scrollHeight - b.clientHeight;" +

		                    	    // pick the scrollable element
		                    	    "if (h2 > h1) {" +
		                    	    "  b.scrollTop = h2 * pct;" +
		                    	    "} else {" +
		                    	    "  de.scrollTop = h1 * pct;" +
		                    	    "}";
		                    js +=  "alert('JS executed!');";
		                    logger.info(String.format("executing: \"%s\"", js ));
		                    webView.getEngine().executeScript(js);
		                }
		            };

		            Object html = webView.getEngine().executeScript("document.documentElement.outerHTML");
		            logger.info(String.format("HTML content length: " + (html != null ? html.toString().length() : "null")));
		            logger.info(String.format("HTML content :%s" , html));
		            logger.info(String.format("Webvire Enging Loadworker state :%s" , loadWorker.getState()));
		            // https://docs.oracle.com/javase/8/javafx/api/javafx/concurrent/Worker.State.html
		            if (loadWorker.getState() == State.SUCCEEDED) {
		                // Page already loaded → execute immediately
		                doScroll.run();
		            } else {
		                // Page still loading → attach listener
		                loadWorker.stateProperty().addListener((obs, oldState, newState) -> {
		                    if (newState == State.SUCCEEDED) {
		                        doScroll.run();
		                    }
		                });
		            }
		        }
		    });
		});

		Label versionLabel = new Label(String.format("Version: %s", version));
		versionLabel.setFont(Font.font("Arial", 12));
		versionLabel.setTextFill(Color.BLACK);
		versionLabel.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-padding: 2;");

		HBox bottomBox = new HBox(versionLabel);
		bottomBox.getChildren().add(0, btnSync);  
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
	public String getResourcePath(String resourceFileName) {
		String prefix = "file://";
		final String resourcePath = String
				.format("%s/%s/src/main/resources/%s", prefix, System.getProperty("user.dir"), resourceFileName)
				.replaceAll("\\\\", "/");
        logger.info("Project based resource path: " + resourcePath);
		return resourcePath;
	}

	private ScrollBar findVerticalScrollbar(TextArea textArea) {
		Set<Node> nodes = textArea.lookupAll(".scroll-bar");
	    for (Node node : nodes) {
	        if (node instanceof ScrollBar) {
	            ScrollBar sb = (ScrollBar) node;
	            if (sb.getOrientation() == Orientation.VERTICAL) {
	                return sb;
	            }
	        }
	    }
	    return null;
    }
	
}


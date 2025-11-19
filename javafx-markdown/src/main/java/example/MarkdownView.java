package example;

import java.net.URL;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.VBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("restriction")
public class MarkdownView extends VBox {

    private final Logger logger = LoggerFactory
            .getLogger(MarkdownView.class);


	private SimpleStringProperty mdString = new SimpleStringProperty("");

	public MarkdownView(String mdString) {
		this.mdString.set(mdString);
		this.mdString.addListener((p, o, n) -> updateContent());
		getStylesheets().add("/css/mdfx.css");
		updateContent();
	}

	public MarkdownView() {
		this("");
	}

	private void updateContent() {
		MDFXNodeHelper content = new MDFXNodeHelper(this, mdString.getValue());
		getChildren().clear();
		getChildren().add(content);
	}

	public StringProperty mdStringProperty() {
		return mdString;
	}

	public void setMdString(String mdString) {
		this.mdString.set(mdString);
	}

	public String getMdString() {
		return mdString.get();
	}

	public boolean showChapter(int[] currentChapter) {
		return true;
	}

	public void setLink(Node node, String link, String description) {
		// TODO
		// com.jpro.web.Util.setLink(node, link, scala.Option.apply(description));
	}

	public Node generateImage(String url) {
		if (url.isEmpty()) {
			return new Group();
		} else {
			return new ImageView(generateImageWithPlaceholder(url));
		}
	}
	
	// wrap the image generation in a try/catch, 
	// when an exception occurs, substitute a placeholder Image instead of 
	// letting the exception flow out and crash the JavaFX renderer
	
	public Image generateImageWithPlaceholder(String url) {
	    if (url == null || url.isEmpty()) {
	        return getPlaceholder();
	    }

	    try {
	        // sanitize input
	    	url = url.trim().replace("`", "");
	        if (url.startsWith("<") && url.endsWith(">")) {
	        	url = url.substring(1, url.length() - 1).trim();
	        }

	        // attempt to create the Image
	        Image image = new Image(url, true); // background loading
	        if (image.isError()) {
	            logger.warn("Image reported error for url: " + url);
	            return getPlaceholder();
	        }
	        return image;

	    } catch (IllegalArgumentException | NullPointerException e) {
	        logger.warn("Failed to load image: " + url + " -> " + e.getMessage());
	        return getPlaceholder();
	    }
	}

	private Image getPlaceholder() {
	    try {
	        URL res = getClass().getResource("/images/placeholder.png");
	        if (res != null) return new Image(res.toExternalForm(), true);

	        // NOTE: can generate 1x1 transparent image on the fly
	        WritableImage writableImage = new WritableImage(50, 50);
	        PixelWriter pixelWriter = writableImage.getPixelWriter();
	        for (int y = 0; y < 50; y++) {
	            for (int x = 0; x < 50; x++) {
	            	pixelWriter.setArgb(x, y, 0xFFCCCCCC);
	            }
	        }
	        return writableImage;
	    } catch (Exception e) {
	        logger.error("Failed to create placeholder image: ", e);
	        return null;
	    }
	}

}

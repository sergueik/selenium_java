package example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.VBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("restriction")
public class MarkdownView extends VBox {

	private final Logger logger = LoggerFactory.getLogger(MarkdownView.class);
    
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
			return new ImageView(generateImageWithPlaceholder(translateImageUrl(url)));
		}
	}
	
	private String translateImageUrl(String url) {
	    if (url == null || url.isEmpty()) return url;

	    final Pattern pattern = Pattern.compile(
	    	    "https?://github\\.com/(?<user>[^/]+)/(?<repo>[^/]+)/(?:blob|tree)/(?<branch>[^/]+)/(?<path>.+)",
	    	    Pattern.CASE_INSENSITIVE
	    	);
	    Matcher m = pattern.matcher(url);
	    if (m.matches()) {
	        String user = m.group("user");
	        String repo = m.group("repo");
	        String branch = m.group("branch");
	        String path = m.group("path");

	        // Normalize path (remove possible leading slashes)
	        if (path.startsWith("/")) path = path.substring(1);

	        String translated = String.format("https://raw.githubusercontent.com/%s/%s/%s/%s",
	                                          user, repo, branch, path);
	        logger.warn(String.format("translateImageUrl: %s -> %s", url, translated));
	        return translated;
	    }

	    // return original URL unchanged when no match observed 
	    logger.warn(String.format("translateImageUrl: %s (unchanged)", url));
	    return url;
	}
	
	private String translateImageUrlShort(String url) {

		final String regex = "https?://github\\.com/([^/]+)/([^/]+)/(?:blob|tree)/([^/]+)/(?.+)";	   
	    
	    String translatedUrl = url.replaceAll(
	        regex,
	        "(https://raw.githubusercontent.com/$1/$2/$3/$4)"
	    );
	    logger.info(String.format("translated %s to %s ", url, translatedUrl));
	    return translatedUrl;
	}
	
	
	// if an exception occurs during image generation, substitute a placeholder Image instead of 
	// letting the exception flow out and crash the JavaFX renderer
	
	public Image generateImageWithPlaceholderOld(String url) {
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

	private Image generateImageWithPlaceholder(String url) {
	    try {
	        byte[] data = downloadBytes(url);
	        if (data == null || data.length == 0) {
	            return getPlaceholder();
	        }
	        return new Image(new ByteArrayInputStream(data));
	    } catch (Exception e) {
	        logger.error("Image load failed for: " + url, e);
	        return getPlaceholder();
	    }
	}

	private byte[] downloadBytes(String urlStr) throws IOException {
	    URL url = new URL(urlStr);
	    URLConnection conn = url.openConnection();
	    conn.setRequestProperty("User-Agent", "Mozilla/5.0"); // required!
	    conn.setUseCaches(false);

	    try (InputStream in = conn.getInputStream();
	         ByteArrayOutputStream out = new ByteArrayOutputStream()) {

	        byte[] buf = new byte[8192];
	        int len;
	        while ((len = in.read(buf)) != -1) {
	            out.write(buf, 0, len);
	        }
	        return out.toByteArray();
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

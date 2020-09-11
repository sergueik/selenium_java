package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when a new window is going to be opened, via window.open(), link click, form submission, etc.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WindowOpenEvent {

    /**
    * The URL for the new window.
    */
    private String url;

    /**
    * Window name.
    */
    private String windowName;

    /**
    * An array of enabled window features.
    */
    private java.util.List<String> windowFeatures;

    /**
    * Whether or not it was triggered by user gesture.
    */
    private Boolean userGesture;



}
package jpuppeteer.cdp.cdp.entity.page;

/**
* Navigation history entry.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NavigationEntry {

    /**
    * Unique id of the navigation history entry.
    */
    private Integer id;

    /**
    * URL of the navigation history entry.
    */
    private String url;

    /**
    * URL that the user typed in the url bar.
    */
    private String userTypedURL;

    /**
    * Title of the navigation history entry.
    */
    private String title;

    /**
    * Transition type.
    */
    private String transitionType;



}
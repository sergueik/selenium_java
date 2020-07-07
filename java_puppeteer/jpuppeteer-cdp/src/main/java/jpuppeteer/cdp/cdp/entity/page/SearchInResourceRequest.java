package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SearchInResourceRequest {

    /**
    * Frame id for resource to search in.
    */
    private String frameId;

    /**
    * URL of the resource to search in.
    */
    private String url;

    /**
    * String to search for.
    */
    private String query;

    /**
    * If true, search is case sensitive.
    */
    private Boolean caseSensitive;

    /**
    * If true, treats string parameter as regex.
    */
    private Boolean isRegex;



}
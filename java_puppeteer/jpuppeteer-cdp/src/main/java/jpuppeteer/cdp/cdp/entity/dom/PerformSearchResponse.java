package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PerformSearchResponse {

    /**
    * Unique search session identifier.
    */
    private String searchId;

    /**
    * Number of search results.
    */
    private Integer resultCount;



}
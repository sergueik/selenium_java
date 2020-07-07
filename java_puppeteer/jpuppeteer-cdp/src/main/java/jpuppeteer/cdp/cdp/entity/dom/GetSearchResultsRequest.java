package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetSearchResultsRequest {

    /**
    * Unique search session identifier.
    */
    private String searchId;

    /**
    * Start index of the search result to be returned.
    */
    private Integer fromIndex;

    /**
    * End index of the search result to be returned.
    */
    private Integer toIndex;



}
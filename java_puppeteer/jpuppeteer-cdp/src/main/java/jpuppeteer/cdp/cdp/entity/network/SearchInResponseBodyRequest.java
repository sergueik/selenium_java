package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SearchInResponseBodyRequest {

    /**
    * Identifier of the network response to search.
    */
    private String requestId;

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
package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SearchInResponseBodyResponse {

    /**
    * List of search matches.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.SearchMatch> result;



}
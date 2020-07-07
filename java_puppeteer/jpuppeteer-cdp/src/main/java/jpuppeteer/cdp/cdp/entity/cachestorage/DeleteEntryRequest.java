package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DeleteEntryRequest {

    /**
    * Id of cache where the entry will be deleted.
    */
    private String cacheId;

    /**
    * URL spec of the request.
    */
    private String request;



}
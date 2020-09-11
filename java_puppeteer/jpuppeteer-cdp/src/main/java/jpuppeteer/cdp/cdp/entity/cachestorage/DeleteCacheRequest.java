package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DeleteCacheRequest {

    /**
    * Id of cache for deletion.
    */
    private String cacheId;



}
package jpuppeteer.cdp.cdp.entity.storage;

/**
* A cache has been added/deleted.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CacheStorageListUpdatedEvent {

    /**
    * Origin to update.
    */
    private String origin;



}
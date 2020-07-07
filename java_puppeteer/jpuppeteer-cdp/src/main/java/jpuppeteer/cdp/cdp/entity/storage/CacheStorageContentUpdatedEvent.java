package jpuppeteer.cdp.cdp.entity.storage;

/**
* A cache's contents have been modified.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CacheStorageContentUpdatedEvent {

    /**
    * Origin to update.
    */
    private String origin;

    /**
    * Name of cache in origin.
    */
    private String cacheName;



}
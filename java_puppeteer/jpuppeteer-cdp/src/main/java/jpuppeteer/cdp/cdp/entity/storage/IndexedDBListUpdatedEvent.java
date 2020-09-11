package jpuppeteer.cdp.cdp.entity.storage;

/**
* The origin's IndexedDB database list has been modified.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class IndexedDBListUpdatedEvent {

    /**
    * Origin to update.
    */
    private String origin;



}
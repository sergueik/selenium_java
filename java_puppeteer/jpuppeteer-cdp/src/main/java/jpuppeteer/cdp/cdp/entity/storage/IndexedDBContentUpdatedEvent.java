package jpuppeteer.cdp.cdp.entity.storage;

/**
* The origin's IndexedDB object store has been modified.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class IndexedDBContentUpdatedEvent {

    /**
    * Origin to update.
    */
    private String origin;

    /**
    * Database to update.
    */
    private String databaseName;

    /**
    * ObjectStore to update.
    */
    private String objectStoreName;



}
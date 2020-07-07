package jpuppeteer.cdp.cdp.entity.domstorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DomStorageItemUpdatedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.domstorage.StorageId storageId;

    /**
    */
    private String key;

    /**
    */
    private String oldValue;

    /**
    */
    private String newValue;



}
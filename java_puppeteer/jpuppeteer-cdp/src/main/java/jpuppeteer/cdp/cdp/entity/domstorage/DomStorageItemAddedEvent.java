package jpuppeteer.cdp.cdp.entity.domstorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DomStorageItemAddedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.domstorage.StorageId storageId;

    /**
    */
    private String key;

    /**
    */
    private String newValue;



}
package jpuppeteer.cdp.cdp.entity.storage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ClearDataForOriginRequest {

    /**
    * Security origin.
    */
    private String origin;

    /**
    * Comma separated list of StorageType to clear.
    */
    private String storageTypes;



}
package jpuppeteer.cdp.cdp.entity.storage;

/**
* Usage for a storage type.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class UsageForType {

    /**
    * Name of storage type.
    */
    private String storageType;

    /**
    * Storage usage (bytes).
    */
    private Double usage;



}
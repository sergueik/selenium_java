package jpuppeteer.cdp.cdp.entity.domstorage;

/**
* DOM Storage identifier.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StorageId {

    /**
    * Security origin for the storage.
    */
    private String securityOrigin;

    /**
    * Whether the storage is local storage (not session storage).
    */
    private Boolean isLocalStorage;



}
package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DeleteObjectStoreEntriesRequest {

    /**
    */
    private String securityOrigin;

    /**
    */
    private String databaseName;

    /**
    */
    private String objectStoreName;

    /**
    * Range of entry keys to delete
    */
    private jpuppeteer.cdp.cdp.entity.indexeddb.KeyRange keyRange;



}
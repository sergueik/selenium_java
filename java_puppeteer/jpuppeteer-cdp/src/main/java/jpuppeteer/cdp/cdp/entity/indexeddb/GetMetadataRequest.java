package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetMetadataRequest {

    /**
    * Security origin.
    */
    private String securityOrigin;

    /**
    * Database name.
    */
    private String databaseName;

    /**
    * Object store name.
    */
    private String objectStoreName;



}
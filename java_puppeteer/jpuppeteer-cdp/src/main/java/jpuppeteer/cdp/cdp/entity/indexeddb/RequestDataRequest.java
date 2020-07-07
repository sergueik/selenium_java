package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestDataRequest {

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

    /**
    * Index name, empty string for object store data requests.
    */
    private String indexName;

    /**
    * Number of records to skip.
    */
    private Integer skipCount;

    /**
    * Number of records to fetch.
    */
    private Integer pageSize;

    /**
    * Key range.
    */
    private jpuppeteer.cdp.cdp.entity.indexeddb.KeyRange keyRange;



}
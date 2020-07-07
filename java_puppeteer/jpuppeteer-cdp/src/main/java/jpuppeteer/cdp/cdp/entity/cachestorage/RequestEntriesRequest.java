package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestEntriesRequest {

    /**
    * ID of cache to get entries from.
    */
    private String cacheId;

    /**
    * Number of records to skip.
    */
    private Integer skipCount;

    /**
    * Number of records to fetch.
    */
    private Integer pageSize;

    /**
    * If present, only return the entries containing this substring in the path
    */
    private String pathFilter;



}
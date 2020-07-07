package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestDataResponse {

    /**
    * Array of object store data entries.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.indexeddb.DataEntry> objectStoreDataEntries;

    /**
    * If true, there are more entries to fetch in the given range.
    */
    private Boolean hasMore;



}
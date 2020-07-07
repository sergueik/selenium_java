package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestEntriesResponse {

    /**
    * Array of object store data entries.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.cachestorage.DataEntry> cacheDataEntries;

    /**
    * Count of returned entries from this storage. If pathFilter is empty, it is the count of all entries from this storage.
    */
    private Double returnCount;



}
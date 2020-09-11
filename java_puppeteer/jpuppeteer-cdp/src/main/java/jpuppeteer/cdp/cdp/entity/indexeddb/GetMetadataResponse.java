package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetMetadataResponse {

    /**
    * the entries count
    */
    private Double entriesCount;

    /**
    * the current value of key generator, to become the next inserted key into the object store. Valid if objectStore.autoIncrement is true.
    */
    private Double keyGeneratorValue;



}
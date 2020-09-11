package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* Object store index.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ObjectStoreIndex {

    /**
    * Index name.
    */
    private String name;

    /**
    * Index key path.
    */
    private jpuppeteer.cdp.cdp.entity.indexeddb.KeyPath keyPath;

    /**
    * If true, index is unique.
    */
    private Boolean unique;

    /**
    * If true, index allows multiple entries for a key.
    */
    private Boolean multiEntry;



}
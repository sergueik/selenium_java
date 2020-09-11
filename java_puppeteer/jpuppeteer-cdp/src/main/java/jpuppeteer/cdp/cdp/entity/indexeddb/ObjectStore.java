package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* Object store.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ObjectStore {

    /**
    * Object store name.
    */
    private String name;

    /**
    * Object store key path.
    */
    private jpuppeteer.cdp.cdp.entity.indexeddb.KeyPath keyPath;

    /**
    * If true, object store has auto increment flag set.
    */
    private Boolean autoIncrement;

    /**
    * Indexes in this object store.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.indexeddb.ObjectStoreIndex> indexes;



}
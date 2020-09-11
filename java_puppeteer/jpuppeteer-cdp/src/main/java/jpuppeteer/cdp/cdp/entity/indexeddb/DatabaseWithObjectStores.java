package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* Database with an array of object stores.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DatabaseWithObjectStores {

    /**
    * Database name.
    */
    private String name;

    /**
    * Database version (type is not 'integer', as the standard requires the version number to be 'unsigned long long')
    */
    private Double version;

    /**
    * Object stores in this database.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.indexeddb.ObjectStore> objectStores;



}
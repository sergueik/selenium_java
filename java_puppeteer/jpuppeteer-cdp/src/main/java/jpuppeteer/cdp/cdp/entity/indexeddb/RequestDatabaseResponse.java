package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestDatabaseResponse {

    /**
    * Database with an array of object stores.
    */
    private jpuppeteer.cdp.cdp.entity.indexeddb.DatabaseWithObjectStores databaseWithObjectStores;



}
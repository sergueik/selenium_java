package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestDatabaseNamesResponse {

    /**
    * Database names for origin.
    */
    private java.util.List<String> databaseNames;



}
package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* Data entry.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DataEntry {

    /**
    * Key object.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject key;

    /**
    * Primary key object.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject primaryKey;

    /**
    * Value object.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject value;



}
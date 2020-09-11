package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* Key path.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class KeyPath {

    /**
    * Key path type.
    */
    private String type;

    /**
    * String value.
    */
    private String string;

    /**
    * Array value.
    */
    private java.util.List<String> array;



}
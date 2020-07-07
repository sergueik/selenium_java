package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* Key.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Key {

    /**
    * Key type.
    */
    private String type;

    /**
    * Number value.
    */
    private Double number;

    /**
    * String value.
    */
    private String string;

    /**
    * Date value.
    */
    private Double date;

    /**
    * Array value.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.indexeddb.Key> array;



}
package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Object containing abbreviated remote object value.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ObjectPreview {

    /**
    * Object type.
    */
    private String type;

    /**
    * Object subtype hint. Specified for `object` type values only.
    */
    private String subtype;

    /**
    * String representation of the object.
    */
    private String description;

    /**
    * True iff some of the properties or entries of the original object did not fit.
    */
    private Boolean overflow;

    /**
    * List of the properties.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.PropertyPreview> properties;

    /**
    * List of the entries. Specified for `map` and `set` subtype values only.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.EntryPreview> entries;



}
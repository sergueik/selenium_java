package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* A name/value pair.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NameValue {

    /**
    * Attribute/property name.
    */
    private String name;

    /**
    * Attribute/property value.
    */
    private String value;



}
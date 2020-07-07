package jpuppeteer.cdp.cdp.entity.accessibility;

/**
* A single source for a computed AX property.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AXValueSource {

    /**
    * What type of source this is.
    */
    private String type;

    /**
    * The value of this property source.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue value;

    /**
    * The name of the relevant attribute, if any.
    */
    private String attribute;

    /**
    * The value of the relevant attribute, if any.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue attributeValue;

    /**
    * Whether this source is superseded by a higher priority source.
    */
    private Boolean superseded;

    /**
    * The native markup source for this value, e.g. a <label> element.
    */
    private String nativeSource;

    /**
    * The value, such as a node or node list, of the native source.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue nativeSourceValue;

    /**
    * Whether the value for this property is invalid.
    */
    private Boolean invalid;

    /**
    * Reason for the value being invalid, if it is.
    */
    private String invalidReason;



}
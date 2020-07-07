package jpuppeteer.cdp.cdp.entity.accessibility;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AXProperty {

    /**
    * The name of this property.
    */
    private String name;

    /**
    * The value of this property.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue value;



}
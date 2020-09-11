package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetEffectivePropertyValueForNodeRequest {

    /**
    * The element id for which to set property.
    */
    private Integer nodeId;

    /**
    */
    private String propertyName;

    /**
    */
    private String value;



}
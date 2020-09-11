package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetPropertiesResponse {

    /**
    * Object properties.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.PropertyDescriptor> result;

    /**
    * Internal object properties (only of the element itself).
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.InternalPropertyDescriptor> internalProperties;

    /**
    * Object private properties.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.PrivatePropertyDescriptor> privateProperties;

    /**
    * Exception details.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ExceptionDetails exceptionDetails;



}
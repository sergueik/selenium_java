package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Object internal property descriptor. This property isn't normally visible in JavaScript code.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class InternalPropertyDescriptor {

    /**
    * Conventional property name.
    */
    private String name;

    /**
    * The value associated with the property.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject value;



}
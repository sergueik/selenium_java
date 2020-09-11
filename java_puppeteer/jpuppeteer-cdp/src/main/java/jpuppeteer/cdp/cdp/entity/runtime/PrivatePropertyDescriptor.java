package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Object private field descriptor.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PrivatePropertyDescriptor {

    /**
    * Private property name.
    */
    private String name;

    /**
    * The value associated with the private property.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject value;

    /**
    * A function which serves as a getter for the private property, or `undefined` if there is no getter (accessor descriptors only).
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject get;

    /**
    * A function which serves as a setter for the private property, or `undefined` if there is no setter (accessor descriptors only).
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject set;



}
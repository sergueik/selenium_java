package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Object property descriptor.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PropertyDescriptor {

    /**
    * Property name or symbol description.
    */
    private String name;

    /**
    * The value associated with the property.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject value;

    /**
    * True if the value associated with the property may be changed (data descriptors only).
    */
    private Boolean writable;

    /**
    * A function which serves as a getter for the property, or `undefined` if there is no getter (accessor descriptors only).
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject get;

    /**
    * A function which serves as a setter for the property, or `undefined` if there is no setter (accessor descriptors only).
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject set;

    /**
    * True if the type of this property descriptor may be changed and if the property may be deleted from the corresponding object.
    */
    private Boolean configurable;

    /**
    * True if this property shows up during enumeration of the properties on the corresponding object.
    */
    private Boolean enumerable;

    /**
    * True if the result was thrown during the evaluation.
    */
    private Boolean wasThrown;

    /**
    * True if the property is owned for the object.
    */
    private Boolean isOwn;

    /**
    * Property symbol object, if the property is of the `symbol` type.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject symbol;



}
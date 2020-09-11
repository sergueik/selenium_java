package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Mirror object referencing original JavaScript object.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RemoteObject {

    /**
    * Object type.
    */
    private String type;

    /**
    * Object subtype hint. Specified for `object` type values only.
    */
    private String subtype;

    /**
    * Object class (constructor) name. Specified for `object` type values only.
    */
    private String className;

    /**
    * Remote object value in case of primitive values or JSON values (if it was requested).
    */
    private Object value;

    /**
    * Primitive value which can not be JSON-stringified does not have `value`, but gets this property.
    */
    private String unserializableValue;

    /**
    * String representation of the object.
    */
    private String description;

    /**
    * Unique object identifier (for non-primitive values).
    */
    private String objectId;

    /**
    * Preview containing abbreviated property values. Specified for `object` type values only.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ObjectPreview preview;

    /**
    */
    private jpuppeteer.cdp.cdp.entity.runtime.CustomPreview customPreview;



}
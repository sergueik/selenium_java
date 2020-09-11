package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Represents function call argument. Either remote object id `objectId`, primitive `value`, unserializable primitive value or neither of (for undefined) them should be specified.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CallArgument {

    /**
    * Primitive value or serializable javascript object.
    */
    private Object value;

    /**
    * Primitive value which can not be JSON-stringified.
    */
    private String unserializableValue;

    /**
    * Remote object handle.
    */
    private String objectId;



}
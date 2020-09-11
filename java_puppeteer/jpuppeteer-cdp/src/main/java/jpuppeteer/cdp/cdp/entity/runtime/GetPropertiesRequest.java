package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetPropertiesRequest {

    /**
    * Identifier of the object to return properties for.
    */
    private String objectId;

    /**
    * If true, returns properties belonging only to the element itself, not to its prototype chain.
    */
    private Boolean ownProperties;

    /**
    * If true, returns accessor properties (with getter/setter) only; internal properties are not returned either.
    */
    private Boolean accessorPropertiesOnly;

    /**
    * Whether preview should be generated for the results.
    */
    private Boolean generatePreview;



}
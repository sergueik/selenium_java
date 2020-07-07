package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class QueryObjectsRequest {

    /**
    * Identifier of the prototype to return objects for.
    */
    private String prototypeObjectId;

    /**
    * Symbolic group name that can be used to release the results.
    */
    private String objectGroup;



}
package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AwaitPromiseRequest {

    /**
    * Identifier of the promise.
    */
    private String promiseObjectId;

    /**
    * Whether the result is expected to be a JSON object that should be sent by value.
    */
    private Boolean returnByValue;

    /**
    * Whether preview should be generated for the result.
    */
    private Boolean generatePreview;



}
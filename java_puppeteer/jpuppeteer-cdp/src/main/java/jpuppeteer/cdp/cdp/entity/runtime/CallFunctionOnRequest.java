package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CallFunctionOnRequest {

    /**
    * Declaration of the function to call.
    */
    private String functionDeclaration;

    /**
    * Identifier of the object to call function on. Either objectId or executionContextId should be specified.
    */
    private String objectId;

    /**
    * Call arguments. All call arguments must belong to the same JavaScript world as the target object.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.CallArgument> arguments;

    /**
    * In silent mode exceptions thrown during evaluation are not reported and do not pause execution. Overrides `setPauseOnException` state.
    */
    private Boolean silent;

    /**
    * Whether the result is expected to be a JSON object which should be sent by value.
    */
    private Boolean returnByValue;

    /**
    * Whether preview should be generated for the result.
    */
    private Boolean generatePreview;

    /**
    * Whether execution should be treated as initiated by user in the UI.
    */
    private Boolean userGesture;

    /**
    * Whether execution should `await` for resulting value and return once awaited promise is resolved.
    */
    private Boolean awaitPromise;

    /**
    * Specifies execution context which global object will be used to call function on. Either executionContextId or objectId should be specified.
    */
    private Integer executionContextId;

    /**
    * Symbolic group name that can be used to release multiple objects. If objectGroup is not specified and objectId is, objectGroup will be inherited from object.
    */
    private String objectGroup;



}
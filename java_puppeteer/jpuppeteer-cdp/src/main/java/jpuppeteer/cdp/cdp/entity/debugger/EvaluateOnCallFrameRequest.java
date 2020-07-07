package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EvaluateOnCallFrameRequest {

    /**
    * Call frame identifier to evaluate on.
    */
    private String callFrameId;

    /**
    * Expression to evaluate.
    */
    private String expression;

    /**
    * String object group name to put result into (allows rapid releasing resulting object handles using `releaseObjectGroup`).
    */
    private String objectGroup;

    /**
    * Specifies whether command line API should be available to the evaluated expression, defaults to false.
    */
    private Boolean includeCommandLineAPI;

    /**
    * In silent mode exceptions thrown during evaluation are not reported and do not pause execution. Overrides `setPauseOnException` state.
    */
    private Boolean silent;

    /**
    * Whether the result is expected to be a JSON object that should be sent by value.
    */
    private Boolean returnByValue;

    /**
    * Whether preview should be generated for the result.
    */
    private Boolean generatePreview;

    /**
    * Whether to throw an exception if side effect cannot be ruled out during evaluation.
    */
    private Boolean throwOnSideEffect;

    /**
    * Terminate execution after timing out (number of milliseconds).
    */
    private Double timeout;



}
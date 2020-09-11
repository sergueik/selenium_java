package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EvaluateRequest {

    /**
    * Expression to evaluate.
    */
    private String expression;

    /**
    * Symbolic group name that can be used to release multiple objects.
    */
    private String objectGroup;

    /**
    * Determines whether Command Line API should be available during the evaluation.
    */
    private Boolean includeCommandLineAPI;

    /**
    * In silent mode exceptions thrown during evaluation are not reported and do not pause execution. Overrides `setPauseOnException` state.
    */
    private Boolean silent;

    /**
    * Specifies in which execution context to perform evaluation. If the parameter is omitted the evaluation will be performed in the context of the inspected page.
    */
    private Integer contextId;

    /**
    * Whether the result is expected to be a JSON object that should be sent by value.
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
    * Whether to throw an exception if side effect cannot be ruled out during evaluation. This implies `disableBreaks` below.
    */
    private Boolean throwOnSideEffect;

    /**
    * Terminate execution after timing out (number of milliseconds).
    */
    private Double timeout;

    /**
    * Disable breakpoints during execution.
    */
    private Boolean disableBreaks;

    /**
    * Reserved flag for future REPL mode support. Setting this flag has currently no effect.
    */
    private Boolean replMode;



}
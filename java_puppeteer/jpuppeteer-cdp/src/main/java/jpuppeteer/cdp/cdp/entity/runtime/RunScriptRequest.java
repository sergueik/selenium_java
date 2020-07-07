package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RunScriptRequest {

    /**
    * Id of the script to run.
    */
    private String scriptId;

    /**
    * Specifies in which execution context to perform script run. If the parameter is omitted the evaluation will be performed in the context of the inspected page.
    */
    private Integer executionContextId;

    /**
    * Symbolic group name that can be used to release multiple objects.
    */
    private String objectGroup;

    /**
    * In silent mode exceptions thrown during evaluation are not reported and do not pause execution. Overrides `setPauseOnException` state.
    */
    private Boolean silent;

    /**
    * Determines whether Command Line API should be available during the evaluation.
    */
    private Boolean includeCommandLineAPI;

    /**
    * Whether the result is expected to be a JSON object which should be sent by value.
    */
    private Boolean returnByValue;

    /**
    * Whether preview should be generated for the result.
    */
    private Boolean generatePreview;

    /**
    * Whether execution should `await` for resulting value and return once awaited promise is resolved.
    */
    private Boolean awaitPromise;



}
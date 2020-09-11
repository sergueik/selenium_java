package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Issued when console API was called.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ConsoleAPICalledEvent {

    /**
    * Type of the call.
    */
    private String type;

    /**
    * Call arguments.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.RemoteObject> args;

    /**
    * Identifier of the context where the call was made.
    */
    private Integer executionContextId;

    /**
    * Call timestamp.
    */
    private Double timestamp;

    /**
    * Stack trace captured when the call was made. The async stack chain is automatically reported for the following call types: `assert`, `error`, `trace`, `warning`. For other types the async call chain can be retrieved using `Debugger.getStackTrace` and `stackTrace.parentId` field.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace stackTrace;

    /**
    * Console context descriptor for calls on non-default console context (not console.*): 'anonymous#unique-logger-id' for call on unnamed context, 'name#unique-logger-id' for call on named context.
    */
    private String context;



}
package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Issued when new execution context is created.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExecutionContextCreatedEvent {

    /**
    * A newly created execution context.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ExecutionContextDescription context;



}
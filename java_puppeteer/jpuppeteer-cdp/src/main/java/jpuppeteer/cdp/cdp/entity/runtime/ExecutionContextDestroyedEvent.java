package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Issued when execution context is destroyed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExecutionContextDestroyedEvent {

    /**
    * Id of the destroyed context
    */
    private Integer executionContextId;



}
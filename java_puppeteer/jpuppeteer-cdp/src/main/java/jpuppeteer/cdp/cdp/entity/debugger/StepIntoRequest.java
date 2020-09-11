package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StepIntoRequest {

    /**
    * Debugger will pause on the execution of the first async task which was scheduled before next pause.
    */
    private Boolean breakOnAsyncCall;



}
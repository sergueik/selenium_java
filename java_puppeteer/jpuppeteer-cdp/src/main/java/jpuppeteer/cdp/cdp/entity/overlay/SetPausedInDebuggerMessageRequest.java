package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetPausedInDebuggerMessageRequest {

    /**
    * The message to display, also triggers resume and step over controls.
    */
    private String message;



}
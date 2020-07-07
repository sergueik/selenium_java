package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload) has been closed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class JavascriptDialogClosedEvent {

    /**
    * Whether dialog was confirmed.
    */
    private Boolean result;

    /**
    * User input in case of prompt.
    */
    private String userInput;



}
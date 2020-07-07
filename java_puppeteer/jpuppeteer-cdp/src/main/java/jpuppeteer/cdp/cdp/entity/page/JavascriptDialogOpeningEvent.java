package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload) is about to open.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class JavascriptDialogOpeningEvent {

    /**
    * Frame url.
    */
    private String url;

    /**
    * Message that will be displayed by the dialog.
    */
    private String message;

    /**
    * Dialog type.
    */
    private String type;

    /**
    * True iff browser is capable showing or acting on the given dialog. When browser has no dialog handler for given target, calling alert while Page domain is engaged will stall the page execution. Execution can be resumed via calling Page.handleJavaScriptDialog.
    */
    private Boolean hasBrowserHandler;

    /**
    * Default dialog prompt.
    */
    private String defaultPrompt;



}
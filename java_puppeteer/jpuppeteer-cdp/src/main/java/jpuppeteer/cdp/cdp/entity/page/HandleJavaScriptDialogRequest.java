package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HandleJavaScriptDialogRequest {

    /**
    * Whether to accept or dismiss the dialog.
    */
    private Boolean accept;

    /**
    * The text to enter into the dialog prompt before accepting. Used only if this is a prompt dialog.
    */
    private String promptText;



}
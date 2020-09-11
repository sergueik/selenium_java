package jpuppeteer.chrome.event.page;

import jpuppeteer.cdp.cdp.entity.page.JavascriptDialogOpeningEvent;
import jpuppeteer.chrome.ChromePage;

public class Dialog extends PageEvent {

    private final String type;

    private final String message;

    private final String defaultValue;

    public Dialog(ChromePage page, JavascriptDialogOpeningEvent dialog) {
        super(page);
        this.type = dialog.getType();
        this.message = dialog.getMessage();
        this.defaultValue = dialog.getDefaultPrompt();
    }

    public String type() {
        return type;
    }

    public String message() {
        return message;
    }

    public String defaultValue() {
        return defaultValue;
    }

    public void accept(String value) throws Exception {
        page().handleJavaScriptDialog(true, value);
    }

    public void accept() throws Exception {
        page().handleJavaScriptDialog(true, null);
    }

    public void cancel() throws Exception {
        page().handleJavaScriptDialog(false, null);
    }
}

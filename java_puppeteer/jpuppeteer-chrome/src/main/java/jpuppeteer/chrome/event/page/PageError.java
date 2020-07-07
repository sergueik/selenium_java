package jpuppeteer.chrome.event.page;

import jpuppeteer.cdp.cdp.entity.runtime.ExceptionThrownEvent;
import jpuppeteer.chrome.ChromePage;

public class PageError extends PageEvent {

    private final ExceptionThrownEvent error;

    public PageError(ChromePage page, ExceptionThrownEvent error) {
        super(page);
        this.error = error;
    }

    public ExceptionThrownEvent error() {
        return this.error;
    }
}

package jpuppeteer.chrome.event.context;

import jpuppeteer.chrome.ChromeContext;
import jpuppeteer.chrome.ChromePage;

public class PageCreated extends ContextEvent {

    private final ChromePage page;

    public PageCreated(ChromeContext context, ChromePage page) {
        super(context);
        this.page = page;
    }

    public ChromePage page() {
        return this.page;
    }
}

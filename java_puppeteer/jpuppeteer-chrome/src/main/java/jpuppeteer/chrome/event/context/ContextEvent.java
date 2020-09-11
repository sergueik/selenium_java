package jpuppeteer.chrome.event.context;

import jpuppeteer.chrome.ChromeContext;

public class ContextEvent {

    private final ChromeContext context;

    public ContextEvent(ChromeContext context) {
        this.context = context;
    }

    public ChromeContext context() {
        return context;
    }

}

package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromePage;

public class PageEvent {

    private final ChromePage page;

    public PageEvent(ChromePage page) {
        this.page = page;
    }

    public ChromePage page() {
        return page;
    }
}

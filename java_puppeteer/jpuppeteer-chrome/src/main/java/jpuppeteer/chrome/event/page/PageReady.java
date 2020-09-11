package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromePage;

public class PageReady extends PageEvent {

    private final Double duration;

    public PageReady(ChromePage page, Double duration) {
        super(page);
        this.duration = duration;
    }

    public Double duration() {
        return duration;
    }
}

package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromePage;

public class PageLoaded extends PageEvent {

    private final Double duration;

    public PageLoaded(ChromePage page, Double duration) {
        super(page);
        this.duration = duration;
    }

    public Double duration() {
        return duration;
    }
}

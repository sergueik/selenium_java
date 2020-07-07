package jpuppeteer.chrome.event.page;

import jpuppeteer.cdp.cdp.entity.target.TargetCrashedEvent;
import jpuppeteer.chrome.ChromePage;

public class PageCrashed extends PageEvent {

    private final TargetCrashedEvent error;

    public PageCrashed(ChromePage page, TargetCrashedEvent error) {
        super(page);
        this.error = error;
    }

    public TargetCrashedEvent error() {
        return this.error;
    }
}

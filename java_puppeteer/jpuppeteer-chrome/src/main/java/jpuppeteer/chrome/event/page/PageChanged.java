package jpuppeteer.chrome.event.page;

import jpuppeteer.cdp.cdp.entity.target.TargetInfo;
import jpuppeteer.chrome.ChromePage;

public class PageChanged extends PageEvent {

    private final TargetInfo targetInfo;

    public PageChanged(ChromePage page, TargetInfo targetInfo) {
        super(page);
        this.targetInfo = targetInfo;
    }

    public TargetInfo targetInfo() {
        return this.targetInfo;
    }
}

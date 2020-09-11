package jpuppeteer.chrome.event.page;

import jpuppeteer.cdp.constant.PageLifecyclePhase;
import jpuppeteer.chrome.ChromeFrame;
import jpuppeteer.chrome.ChromePage;

public class FrameLifecycle extends FrameEvent {

    private final PageLifecyclePhase phase;

    public FrameLifecycle(ChromePage page, ChromeFrame frame, PageLifecyclePhase phase) {
        super(page, frame);
        this.phase = phase;
    }

    public PageLifecyclePhase phase() {
        return phase;
    }
}

package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromeFrame;
import jpuppeteer.chrome.ChromePage;

public class FrameEvent extends PageEvent {

    private final ChromeFrame frame;

    public FrameEvent(ChromePage page, ChromeFrame frame) {
        super(page);
        this.frame = frame;
    }

    public ChromeFrame frame() {
        return frame;
    }
}

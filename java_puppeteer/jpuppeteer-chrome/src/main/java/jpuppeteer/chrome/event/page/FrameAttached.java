package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromeFrame;
import jpuppeteer.chrome.ChromePage;

public class FrameAttached extends FrameEvent {

    public FrameAttached(ChromePage page, ChromeFrame frame) {
        super(page, frame);
    }
}

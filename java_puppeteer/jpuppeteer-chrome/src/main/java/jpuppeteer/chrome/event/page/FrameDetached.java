package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromeFrame;
import jpuppeteer.chrome.ChromePage;

public class FrameDetached extends FrameEvent {

    public FrameDetached(ChromePage page, ChromeFrame frame) {
        super(page, frame);
    }
}

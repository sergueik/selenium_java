package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromeFrame;
import jpuppeteer.chrome.ChromePage;

public class FrameNavigated extends FrameEvent {

    public FrameNavigated(ChromePage page, ChromeFrame frame) {
        super(page, frame);
    }
}

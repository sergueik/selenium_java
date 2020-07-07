package jpuppeteer.chrome.event.page;

import jpuppeteer.chrome.ChromePage;

public class PageOpened extends PageEvent {

    private final ChromePage opener;

    public PageOpened(ChromePage page, ChromePage opener) {
        super(page);
        this.opener = opener;
    }

    public ChromePage opener() {
        return opener;
    }
}

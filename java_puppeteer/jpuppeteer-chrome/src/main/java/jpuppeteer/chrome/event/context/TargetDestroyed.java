package jpuppeteer.chrome.event.context;

import jpuppeteer.cdp.CDPEvent;
import jpuppeteer.cdp.cdp.entity.target.TargetDestroyedEvent;
import jpuppeteer.chrome.ChromeContext;

public class TargetDestroyed extends ContextCDPEvent<TargetDestroyedEvent> {

    public TargetDestroyed(ChromeContext context, CDPEvent event) {
        super(context, event);
    }
}

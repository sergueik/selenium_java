package jpuppeteer.chrome.event.context;

import jpuppeteer.cdp.CDPEvent;
import jpuppeteer.cdp.cdp.entity.target.AttachedToTargetEvent;
import jpuppeteer.chrome.ChromeContext;

public class TargetAttached extends ContextCDPEvent<AttachedToTargetEvent> {

    public TargetAttached(ChromeContext context, CDPEvent event) {
        super(context, event);
    }
}

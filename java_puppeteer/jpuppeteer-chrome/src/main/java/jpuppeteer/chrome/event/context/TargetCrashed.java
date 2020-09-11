package jpuppeteer.chrome.event.context;

import jpuppeteer.cdp.CDPEvent;
import jpuppeteer.cdp.cdp.entity.target.TargetCrashedEvent;
import jpuppeteer.chrome.ChromeContext;

public class TargetCrashed extends ContextCDPEvent<TargetCrashedEvent> {

    public TargetCrashed(ChromeContext context, CDPEvent event) {
        super(context, event);
    }
}

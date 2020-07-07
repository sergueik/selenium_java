package jpuppeteer.chrome.event.context;

import jpuppeteer.cdp.CDPEvent;
import jpuppeteer.cdp.cdp.entity.target.TargetInfoChangedEvent;
import jpuppeteer.chrome.ChromeContext;

public class TargetChanged extends ContextCDPEvent<TargetInfoChangedEvent> {

    public TargetChanged(ChromeContext context, CDPEvent event) {
        super(context, event);
    }
}

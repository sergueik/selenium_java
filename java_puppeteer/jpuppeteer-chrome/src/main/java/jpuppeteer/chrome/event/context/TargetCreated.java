package jpuppeteer.chrome.event.context;

import jpuppeteer.cdp.CDPEvent;
import jpuppeteer.cdp.cdp.entity.target.TargetInfo;
import jpuppeteer.chrome.ChromeContext;

public class TargetCreated extends ContextCDPEvent<TargetInfo> {

    public TargetCreated(ChromeContext context, CDPEvent event) {
        super(context, event);
    }
}

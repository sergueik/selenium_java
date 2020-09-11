package jpuppeteer.chrome.event.context;

import jpuppeteer.cdp.CDPEvent;
import jpuppeteer.cdp.cdp.CDPEventType;
import jpuppeteer.chrome.ChromeContext;

public class ContextCDPEvent<T> extends ContextEvent {

    private final String sessionId;

    private final CDPEventType method;

    private final T event;

    public ContextCDPEvent(ChromeContext context, CDPEvent event) {
        super(context);
        this.sessionId = event.getSessionId();
        this.method = event.getMethod();
        this.event = (T) event.getObject(event.getMethod().getClazz());
    }

    public String sessionId() {
        return sessionId;
    }

    public CDPEventType method() {
        return method;
    }

    public T event() {
        return event;
    }


}

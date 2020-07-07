package jpuppeteer.chrome.event.page;

import jpuppeteer.api.browser.ExecutionContext;
import jpuppeteer.cdp.cdp.constant.runtime.ConsoleAPICalledEventType;
import jpuppeteer.cdp.cdp.entity.runtime.StackTrace;
import jpuppeteer.chrome.ChromeBrowserObject;
import jpuppeteer.chrome.ChromePage;

import java.util.List;

public class Console extends PageEvent {

    private final ConsoleAPICalledEventType eventType;

    private final List<ChromeBrowserObject> args;

    private final ExecutionContext execution;

    private final Double timestamp;

    private final StackTrace stackTrace;

    private final String context;

    public Console(
            ChromePage page, ConsoleAPICalledEventType eventType,
            List<ChromeBrowserObject> args, ExecutionContext execution,
            Double timestamp, StackTrace stackTrace, String context) {
        super(page);
        this.eventType = eventType;
        this.args = args;
        this.execution = execution;
        this.timestamp = timestamp;
        this.stackTrace = stackTrace;
        this.context = context;
    }

    public ConsoleAPICalledEventType type() {
        return eventType;
    }

    public List<ChromeBrowserObject> args() {
        return args;
    }

    public ExecutionContext execution() {
        return execution;
    }

    public Double timestamp() {
        return timestamp;
    }

    public StackTrace stackTrace() {
        return stackTrace;
    }

    public String context() {
        return context;
    }
}

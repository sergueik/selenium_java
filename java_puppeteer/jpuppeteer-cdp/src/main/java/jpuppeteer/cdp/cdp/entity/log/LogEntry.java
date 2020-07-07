package jpuppeteer.cdp.cdp.entity.log;

/**
* Log entry.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LogEntry {

    /**
    * Log entry source.
    */
    private String source;

    /**
    * Log entry severity.
    */
    private String level;

    /**
    * Logged text.
    */
    private String text;

    /**
    * Timestamp when this entry was added.
    */
    private Double timestamp;

    /**
    * URL of the resource if known.
    */
    private String url;

    /**
    * Line number in the resource.
    */
    private Integer lineNumber;

    /**
    * JavaScript stack trace.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace stackTrace;

    /**
    * Identifier of the network request associated with this entry.
    */
    private String networkRequestId;

    /**
    * Identifier of the worker associated with this entry.
    */
    private String workerId;

    /**
    * Call arguments.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.runtime.RemoteObject> args;



}
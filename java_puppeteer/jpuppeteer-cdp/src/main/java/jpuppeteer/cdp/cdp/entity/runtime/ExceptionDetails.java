package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Detailed information about exception (or error) that was thrown during script compilation or execution.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExceptionDetails {

    /**
    * Exception id.
    */
    private Integer exceptionId;

    /**
    * Exception text, which should be used together with exception object when available.
    */
    private String text;

    /**
    * Line number of the exception location (0-based).
    */
    private Integer lineNumber;

    /**
    * Column number of the exception location (0-based).
    */
    private Integer columnNumber;

    /**
    * Script ID of the exception location.
    */
    private String scriptId;

    /**
    * URL of the exception location, to be used when the script was not reported.
    */
    private String url;

    /**
    * JavaScript stack trace if available.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace stackTrace;

    /**
    * Exception object if available.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject exception;

    /**
    * Identifier of the context where exception happened.
    */
    private Integer executionContextId;



}
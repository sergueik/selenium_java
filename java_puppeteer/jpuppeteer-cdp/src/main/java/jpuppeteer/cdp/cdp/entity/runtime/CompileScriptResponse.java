package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CompileScriptResponse {

    /**
    * Id of the script.
    */
    private String scriptId;

    /**
    * Exception details.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ExceptionDetails exceptionDetails;



}
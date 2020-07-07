package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetNodeStackTracesResponse {

    /**
    * Creation stack trace, if available.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace creation;



}
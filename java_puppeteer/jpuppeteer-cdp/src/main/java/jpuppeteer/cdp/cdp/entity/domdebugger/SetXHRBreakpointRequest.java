package jpuppeteer.cdp.cdp.entity.domdebugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetXHRBreakpointRequest {

    /**
    * Resource URL substring. All XHRs having this substring in the URL will get stopped upon.
    */
    private String url;



}
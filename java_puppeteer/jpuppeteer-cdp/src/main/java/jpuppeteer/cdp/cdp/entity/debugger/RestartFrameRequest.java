package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RestartFrameRequest {

    /**
    * Call frame identifier to evaluate on.
    */
    private String callFrameId;



}
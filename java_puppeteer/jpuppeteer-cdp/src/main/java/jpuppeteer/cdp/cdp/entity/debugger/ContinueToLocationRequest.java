package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContinueToLocationRequest {

    /**
    * Location to continue to.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location location;

    /**
    */
    private String targetCallFrames;



}
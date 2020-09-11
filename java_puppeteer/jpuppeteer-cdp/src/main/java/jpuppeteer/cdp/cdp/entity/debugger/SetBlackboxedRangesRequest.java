package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBlackboxedRangesRequest {

    /**
    * Id of the script.
    */
    private String scriptId;

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.ScriptPosition> positions;



}
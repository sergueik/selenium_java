package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetScriptSourceRequest {

    /**
    * Id of the script to get source for.
    */
    private String scriptId;



}
package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ReloadRequest {

    /**
    * If true, browser cache is ignored (as if the user pressed Shift+refresh).
    */
    private Boolean ignoreCache;

    /**
    * If set, the script will be injected into all frames of the inspected page after reload. Argument will be ignored if reloading dataURL origin.
    */
    private String scriptToEvaluateOnLoad;



}
package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetWindowForTargetRequest {

    /**
    * Devtools agent host id. If called as a part of the session, associated targetId is used.
    */
    private String targetId;



}
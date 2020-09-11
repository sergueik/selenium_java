package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PushNodeByPathToFrontendRequest {

    /**
    * Path to node in the proprietary format.
    */
    private String path;



}
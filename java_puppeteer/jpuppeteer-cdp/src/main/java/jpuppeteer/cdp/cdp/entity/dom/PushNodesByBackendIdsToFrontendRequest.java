package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PushNodesByBackendIdsToFrontendRequest {

    /**
    * The array of backend node ids.
    */
    private java.util.List<Integer> backendNodeIds;



}
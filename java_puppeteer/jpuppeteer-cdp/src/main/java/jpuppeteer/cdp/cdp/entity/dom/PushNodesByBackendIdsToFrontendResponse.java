package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PushNodesByBackendIdsToFrontendResponse {

    /**
    * The array of ids of pushed nodes that correspond to the backend ids specified in backendNodeIds.
    */
    private java.util.List<Integer> nodeIds;



}
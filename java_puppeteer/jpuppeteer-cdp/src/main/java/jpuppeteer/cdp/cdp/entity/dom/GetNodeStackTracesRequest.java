package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetNodeStackTracesRequest {

    /**
    * Id of the node to get stack traces for.
    */
    private Integer nodeId;



}
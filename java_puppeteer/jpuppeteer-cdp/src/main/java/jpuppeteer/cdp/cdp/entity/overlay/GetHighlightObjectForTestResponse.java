package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetHighlightObjectForTestResponse {

    /**
    * Highlight data for the node.
    */
    private java.util.Map<String, Object> highlight;



}
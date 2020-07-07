package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetHighlightObjectForTestRequest {

    /**
    * Id of the node to get highlight object for.
    */
    private Integer nodeId;

    /**
    * Whether to include distance info.
    */
    private Boolean includeDistance;

    /**
    * Whether to include style info.
    */
    private Boolean includeStyle;



}
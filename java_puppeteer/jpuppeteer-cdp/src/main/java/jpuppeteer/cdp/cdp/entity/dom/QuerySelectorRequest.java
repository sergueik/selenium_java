package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class QuerySelectorRequest {

    /**
    * Id of the node to query upon.
    */
    private Integer nodeId;

    /**
    * Selector string.
    */
    private String selector;



}
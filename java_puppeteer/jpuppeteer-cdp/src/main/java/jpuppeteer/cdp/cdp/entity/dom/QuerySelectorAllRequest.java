package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class QuerySelectorAllRequest {

    /**
    * Id of the node to query upon.
    */
    private Integer nodeId;

    /**
    * Selector string.
    */
    private String selector;



}
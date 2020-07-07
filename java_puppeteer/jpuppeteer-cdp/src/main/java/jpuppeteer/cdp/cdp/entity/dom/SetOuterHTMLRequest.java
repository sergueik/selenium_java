package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetOuterHTMLRequest {

    /**
    * Id of the node to set markup for.
    */
    private Integer nodeId;

    /**
    * Outer HTML markup to set.
    */
    private String outerHTML;



}
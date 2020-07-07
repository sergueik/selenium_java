package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetInspectedNodeRequest {

    /**
    * DOM node id to be accessible by means of $x command line API.
    */
    private Integer nodeId;



}
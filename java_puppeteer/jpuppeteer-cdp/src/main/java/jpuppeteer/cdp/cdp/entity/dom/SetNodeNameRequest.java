package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetNodeNameRequest {

    /**
    * Id of the node to set name for.
    */
    private Integer nodeId;

    /**
    * New node's name.
    */
    private String name;



}
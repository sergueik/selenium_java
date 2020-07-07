package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetNodeValueRequest {

    /**
    * Id of the node to set value for.
    */
    private Integer nodeId;

    /**
    * New node's value.
    */
    private String value;



}
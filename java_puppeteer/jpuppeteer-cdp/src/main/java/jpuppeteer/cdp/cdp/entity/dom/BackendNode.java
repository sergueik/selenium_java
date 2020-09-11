package jpuppeteer.cdp.cdp.entity.dom;

/**
* Backend node with a friendly name.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BackendNode {

    /**
    * `Node`'s nodeType.
    */
    private Integer nodeType;

    /**
    * `Node`'s nodeName.
    */
    private String nodeName;

    /**
    */
    private Integer backendNodeId;



}
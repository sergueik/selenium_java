package jpuppeteer.cdp.cdp.entity.dom;

/**
* Called when shadow root is pushed into the element.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ShadowRootPushedEvent {

    /**
    * Host element id.
    */
    private Integer hostId;

    /**
    * Shadow root.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Node root;



}
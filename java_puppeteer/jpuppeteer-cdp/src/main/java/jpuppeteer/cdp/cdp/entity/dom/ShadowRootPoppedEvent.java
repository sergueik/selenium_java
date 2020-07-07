package jpuppeteer.cdp.cdp.entity.dom;

/**
* Called when shadow root is popped from the element.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ShadowRootPoppedEvent {

    /**
    * Host element id.
    */
    private Integer hostId;

    /**
    * Shadow root id.
    */
    private Integer rootId;



}
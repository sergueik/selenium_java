package jpuppeteer.cdp.cdp.entity.dom;

/**
* Called when a pseudo element is added to an element.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PseudoElementAddedEvent {

    /**
    * Pseudo element's parent element id.
    */
    private Integer parentId;

    /**
    * The added pseudo element.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Node pseudoElement;



}
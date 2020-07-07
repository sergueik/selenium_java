package jpuppeteer.cdp.cdp.entity.dom;

/**
* Called when a pseudo element is removed from an element.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PseudoElementRemovedEvent {

    /**
    * Pseudo element's parent element id.
    */
    private Integer parentId;

    /**
    * The removed pseudo element id.
    */
    private Integer pseudoElementId;



}
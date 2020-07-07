package jpuppeteer.cdp.cdp.entity.dom;

/**
* Fired when `Element`'s inline style is modified via a CSS property modification.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class InlineStyleInvalidatedEvent {

    /**
    * Ids of the nodes for which the inline styles have been invalidated.
    */
    private java.util.List<Integer> nodeIds;



}
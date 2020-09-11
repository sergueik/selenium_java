package jpuppeteer.cdp.cdp.entity.dom;

/**
* Mirrors `DOMCharacterDataModified` event.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CharacterDataModifiedEvent {

    /**
    * Id of the node that has changed.
    */
    private Integer nodeId;

    /**
    * New text value.
    */
    private String characterData;



}
package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* Details of post layout rendered text positions. The exact layout should not be regarded as stable and may change between versions.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class InlineTextBox {

    /**
    * The bounding box in document coordinates. Note that scroll offset of the document is ignored.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect boundingBox;

    /**
    * The starting index in characters, for this post layout textbox substring. Characters that would be represented as a surrogate pair in UTF-16 have length 2.
    */
    private Integer startCharacterIndex;

    /**
    * The number of characters in this post layout textbox substring. Characters that would be represented as a surrogate pair in UTF-16 have length 2.
    */
    private Integer numCharacters;



}
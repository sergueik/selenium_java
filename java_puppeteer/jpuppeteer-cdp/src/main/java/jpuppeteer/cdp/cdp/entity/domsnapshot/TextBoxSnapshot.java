package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* Table of details of the post layout rendered text positions. The exact layout should not be regarded as stable and may change between versions.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TextBoxSnapshot {

    /**
    * Index of the layout tree node that owns this box collection.
    */
    private java.util.List<Integer> layoutIndex;

    /**
    * The absolute position bounding box.
    */
    private java.util.List<java.util.List<Double>> bounds;

    /**
    * The starting index in characters, for this post layout textbox substring. Characters that would be represented as a surrogate pair in UTF-16 have length 2.
    */
    private java.util.List<Integer> start;

    /**
    * The number of characters in this post layout textbox substring. Characters that would be represented as a surrogate pair in UTF-16 have length 2.
    */
    private java.util.List<Integer> length;



}
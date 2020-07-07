package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* Table of details of an element in the DOM tree with a LayoutObject.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LayoutTreeSnapshot {

    /**
    * Index of the corresponding node in the `NodeTreeSnapshot` array returned by `captureSnapshot`.
    */
    private java.util.List<Integer> nodeIndex;

    /**
    * Array of indexes specifying computed style strings, filtered according to the `computedStyles` parameter passed to `captureSnapshot`.
    */
    private java.util.List<java.util.List<Integer>> styles;

    /**
    * The absolute position bounding box.
    */
    private java.util.List<java.util.List<Double>> bounds;

    /**
    * Contents of the LayoutText, if any.
    */
    private java.util.List<Integer> text;

    /**
    * Stacking context information.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.RareBooleanData stackingContexts;

    /**
    * Global paint order index, which is determined by the stacking order of the nodes. Nodes that are painted together will have the same index. Only provided if includePaintOrder in captureSnapshot was true.
    */
    private java.util.List<Integer> paintOrders;

    /**
    * The offset rect of nodes. Only available when includeDOMRects is set to true
    */
    private java.util.List<java.util.List<Double>> offsetRects;

    /**
    * The scroll rect of nodes. Only available when includeDOMRects is set to true
    */
    private java.util.List<java.util.List<Double>> scrollRects;

    /**
    * The client rect of nodes. Only available when includeDOMRects is set to true
    */
    private java.util.List<java.util.List<Double>> clientRects;



}
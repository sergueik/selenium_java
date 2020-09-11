package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* Details of an element in the DOM tree with a LayoutObject.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LayoutTreeNode {

    /**
    * The index of the related DOM node in the `domNodes` array returned by `getSnapshot`.
    */
    private Integer domNodeIndex;

    /**
    * The bounding box in document coordinates. Note that scroll offset of the document is ignored.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect boundingBox;

    /**
    * Contents of the LayoutText, if any.
    */
    private String layoutText;

    /**
    * The post-layout inline text nodes, if any.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.domsnapshot.InlineTextBox> inlineTextNodes;

    /**
    * Index into the `computedStyles` array returned by `getSnapshot`.
    */
    private Integer styleIndex;

    /**
    * Global paint order index, which is determined by the stacking order of the nodes. Nodes that are painted together will have the same index. Only provided if includePaintOrder in getSnapshot was true.
    */
    private Integer paintOrder;

    /**
    * Set to true to indicate the element begins a new stacking context.
    */
    private Boolean isStackingContext;



}
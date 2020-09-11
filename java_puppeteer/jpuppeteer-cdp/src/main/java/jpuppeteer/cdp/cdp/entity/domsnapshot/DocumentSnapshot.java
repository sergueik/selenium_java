package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* Document snapshot.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DocumentSnapshot {

    /**
    * Document URL that `Document` or `FrameOwner` node points to.
    */
    private Integer documentURL;

    /**
    * Document title.
    */
    private Integer title;

    /**
    * Base URL that `Document` or `FrameOwner` node uses for URL completion.
    */
    private Integer baseURL;

    /**
    * Contains the document's content language.
    */
    private Integer contentLanguage;

    /**
    * Contains the document's character set encoding.
    */
    private Integer encodingName;

    /**
    * `DocumentType` node's publicId.
    */
    private Integer publicId;

    /**
    * `DocumentType` node's systemId.
    */
    private Integer systemId;

    /**
    * Frame ID for frame owner elements and also for the document node.
    */
    private Integer frameId;

    /**
    * A table with dom nodes.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.NodeTreeSnapshot nodes;

    /**
    * The nodes in the layout tree.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.LayoutTreeSnapshot layout;

    /**
    * The post-layout inline text nodes.
    */
    private jpuppeteer.cdp.cdp.entity.domsnapshot.TextBoxSnapshot textBoxes;

    /**
    * Horizontal scroll offset.
    */
    private Double scrollOffsetX;

    /**
    * Vertical scroll offset.
    */
    private Double scrollOffsetY;

    /**
    * Document content width.
    */
    private Double contentWidth;

    /**
    * Document content height.
    */
    private Double contentHeight;



}
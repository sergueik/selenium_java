package jpuppeteer.cdp.cdp.entity.css;

/**
* CSS stylesheet metainformation.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CSSStyleSheetHeader {

    /**
    * The stylesheet identifier.
    */
    private String styleSheetId;

    /**
    * Owner frame identifier.
    */
    private String frameId;

    /**
    * Stylesheet resource URL.
    */
    private String sourceURL;

    /**
    * URL of source map associated with the stylesheet (if any).
    */
    private String sourceMapURL;

    /**
    * Stylesheet origin.
    */
    private String origin;

    /**
    * Stylesheet title.
    */
    private String title;

    /**
    * The backend id for the owner node of the stylesheet.
    */
    private Integer ownerNode;

    /**
    * Denotes whether the stylesheet is disabled.
    */
    private Boolean disabled;

    /**
    * Whether the sourceURL field value comes from the sourceURL comment.
    */
    private Boolean hasSourceURL;

    /**
    * Whether this stylesheet is created for STYLE tag by parser. This flag is not set for document.written STYLE tags.
    */
    private Boolean isInline;

    /**
    * Line offset of the stylesheet within the resource (zero based).
    */
    private Double startLine;

    /**
    * Column offset of the stylesheet within the resource (zero based).
    */
    private Double startColumn;

    /**
    * Size of the content (in characters).
    */
    private Double length;

    /**
    * Line offset of the end of the stylesheet within the resource (zero based).
    */
    private Double endLine;

    /**
    * Column offset of the end of the stylesheet within the resource (zero based).
    */
    private Double endColumn;



}
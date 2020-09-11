package jpuppeteer.cdp.cdp.entity.css;

/**
* CSS media rule descriptor.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CSSMedia {

    /**
    * Media query text.
    */
    private String text;

    /**
    * Source of the media query: "mediaRule" if specified by a @media rule, "importRule" if specified by an @import rule, "linkedSheet" if specified by a "media" attribute in a linked stylesheet's LINK tag, "inlineSheet" if specified by a "media" attribute in an inline stylesheet's STYLE tag.
    */
    private String source;

    /**
    * URL of the document containing the media query description.
    */
    private String sourceURL;

    /**
    * The associated rule (@media or @import) header range in the enclosing stylesheet (if available).
    */
    private jpuppeteer.cdp.cdp.entity.css.SourceRange range;

    /**
    * Identifier of the stylesheet containing this object (if exists).
    */
    private String styleSheetId;

    /**
    * Array of media queries.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.MediaQuery> mediaList;



}
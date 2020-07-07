package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetInlineStylesForNodeResponse {

    /**
    * Inline style for the specified DOM node.
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyle inlineStyle;

    /**
    * Attribute-defined element style (e.g. resulting from "width=20 height=100%").
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyle attributesStyle;



}
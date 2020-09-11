package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetComputedStyleForNodeResponse {

    /**
    * Computed style for the specified DOM node.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.CSSComputedStyleProperty> computedStyle;



}
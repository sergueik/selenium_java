package jpuppeteer.cdp.cdp.entity.css;

/**
* Inherited CSS rule collection from ancestor node.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class InheritedStyleEntry {

    /**
    * The ancestor node's inline style, if any, in the style inheritance chain.
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyle inlineStyle;

    /**
    * Matches of CSS rules matching the ancestor node in the style inheritance chain.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.RuleMatch> matchedCSSRules;



}
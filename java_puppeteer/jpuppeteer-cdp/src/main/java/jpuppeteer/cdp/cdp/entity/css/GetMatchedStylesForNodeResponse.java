package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetMatchedStylesForNodeResponse {

    /**
    * Inline style for the specified DOM node.
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyle inlineStyle;

    /**
    * Attribute-defined element style (e.g. resulting from "width=20 height=100%").
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyle attributesStyle;

    /**
    * CSS rules matching this node, from all applicable stylesheets.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.RuleMatch> matchedCSSRules;

    /**
    * Pseudo style matches for this node.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.PseudoElementMatches> pseudoElements;

    /**
    * A chain of inherited styles (from the immediate node parent up to the DOM tree root).
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.InheritedStyleEntry> inherited;

    /**
    * A list of CSS keyframed animations matching this node.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.CSSKeyframesRule> cssKeyframesRules;



}
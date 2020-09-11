package jpuppeteer.cdp.cdp.entity.css;

/**
* Match data for a CSS rule.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RuleMatch {

    /**
    * CSS rule in the match.
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSRule rule;

    /**
    * Matching selector indices in the rule's selectorList selectors (0-based).
    */
    private java.util.List<Integer> matchingSelectors;



}
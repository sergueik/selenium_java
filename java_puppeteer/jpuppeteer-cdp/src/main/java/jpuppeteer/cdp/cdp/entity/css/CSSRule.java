package jpuppeteer.cdp.cdp.entity.css;

/**
* CSS rule representation.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CSSRule {

    /**
    * The css style sheet identifier (absent for user agent stylesheet and user-specified stylesheet rules) this rule came from.
    */
    private String styleSheetId;

    /**
    * Rule selector data.
    */
    private jpuppeteer.cdp.cdp.entity.css.SelectorList selectorList;

    /**
    * Parent stylesheet's origin.
    */
    private String origin;

    /**
    * Associated style declaration.
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyle style;

    /**
    * Media list array (for rules involving media queries). The array enumerates media queries starting with the innermost one, going outwards.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.CSSMedia> media;



}
package jpuppeteer.cdp.cdp.entity.css;

/**
* CSS style representation.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CSSStyle {

    /**
    * The css style sheet identifier (absent for user agent stylesheet and user-specified stylesheet rules) this rule came from.
    */
    private String styleSheetId;

    /**
    * CSS properties in the style.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.CSSProperty> cssProperties;

    /**
    * Computed values for all shorthands found in the style.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.ShorthandEntry> shorthandEntries;

    /**
    * Style declaration text (if available).
    */
    private String cssText;

    /**
    * Style declaration range in the enclosing stylesheet (if available).
    */
    private jpuppeteer.cdp.cdp.entity.css.SourceRange range;



}
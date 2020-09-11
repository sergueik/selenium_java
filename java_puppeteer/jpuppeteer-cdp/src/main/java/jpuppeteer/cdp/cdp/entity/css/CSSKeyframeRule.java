package jpuppeteer.cdp.cdp.entity.css;

/**
* CSS keyframe rule representation.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CSSKeyframeRule {

    /**
    * The css style sheet identifier (absent for user agent stylesheet and user-specified stylesheet rules) this rule came from.
    */
    private String styleSheetId;

    /**
    * Parent stylesheet's origin.
    */
    private String origin;

    /**
    * Associated key text.
    */
    private jpuppeteer.cdp.cdp.entity.css.Value keyText;

    /**
    * Associated style declaration.
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyle style;



}
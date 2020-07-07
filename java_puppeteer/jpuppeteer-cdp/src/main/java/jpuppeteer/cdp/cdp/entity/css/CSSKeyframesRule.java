package jpuppeteer.cdp.cdp.entity.css;

/**
* CSS keyframes rule representation.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CSSKeyframesRule {

    /**
    * Animation name.
    */
    private jpuppeteer.cdp.cdp.entity.css.Value animationName;

    /**
    * List of keyframes.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.CSSKeyframeRule> keyframes;



}
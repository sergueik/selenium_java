package jpuppeteer.cdp.cdp.entity.animation;

/**
* Keyframes Rule
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class KeyframesRule {

    /**
    * CSS keyframed animation's name.
    */
    private String name;

    /**
    * List of animation keyframes.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.animation.KeyframeStyle> keyframes;



}
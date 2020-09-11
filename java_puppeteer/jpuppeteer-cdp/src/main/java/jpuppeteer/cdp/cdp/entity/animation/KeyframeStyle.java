package jpuppeteer.cdp.cdp.entity.animation;

/**
* Keyframe Style
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class KeyframeStyle {

    /**
    * Keyframe's time offset.
    */
    private String offset;

    /**
    * `AnimationEffect`'s timing function.
    */
    private String easing;



}
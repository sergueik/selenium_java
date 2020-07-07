package jpuppeteer.cdp.cdp.entity.animation;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetTimingRequest {

    /**
    * Animation id.
    */
    private String animationId;

    /**
    * Duration of the animation.
    */
    private Double duration;

    /**
    * Delay of the animation.
    */
    private Double delay;



}
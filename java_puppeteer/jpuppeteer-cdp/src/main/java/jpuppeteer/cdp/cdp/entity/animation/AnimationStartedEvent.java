package jpuppeteer.cdp.cdp.entity.animation;

/**
* Event for animation that has been started.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AnimationStartedEvent {

    /**
    * Animation that was started.
    */
    private jpuppeteer.cdp.cdp.entity.animation.Animation animation;



}
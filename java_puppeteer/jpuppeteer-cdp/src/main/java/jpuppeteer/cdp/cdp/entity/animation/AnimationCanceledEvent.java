package jpuppeteer.cdp.cdp.entity.animation;

/**
* Event for when an animation has been cancelled.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AnimationCanceledEvent {

    /**
    * Id of the animation that was cancelled.
    */
    private String id;



}
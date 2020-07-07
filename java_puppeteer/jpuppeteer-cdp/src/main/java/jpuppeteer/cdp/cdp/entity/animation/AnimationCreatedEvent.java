package jpuppeteer.cdp.cdp.entity.animation;

/**
* Event for each animation that has been created.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AnimationCreatedEvent {

    /**
    * Id of the animation that was created.
    */
    private String id;



}
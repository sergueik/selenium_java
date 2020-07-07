package jpuppeteer.cdp.cdp.entity.animation;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetPausedRequest {

    /**
    * Animations to set the pause state of.
    */
    private java.util.List<String> animations;

    /**
    * Paused state to set to.
    */
    private Boolean paused;



}
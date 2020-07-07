package jpuppeteer.cdp.cdp.entity.animation;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SeekAnimationsRequest {

    /**
    * List of animation ids to seek.
    */
    private java.util.List<String> animations;

    /**
    * Set the current time of each animation.
    */
    private Double currentTime;



}
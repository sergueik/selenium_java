package jpuppeteer.cdp.cdp.entity.media;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PlayerEvent {

    /**
    */
    private String type;

    /**
    * Events are timestamped relative to the start of the player creation not relative to the start of playback.
    */
    private Double timestamp;

    /**
    */
    private String name;

    /**
    */
    private String value;



}
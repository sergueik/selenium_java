package jpuppeteer.cdp.cdp.entity.animation;

/**
* Animation instance.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Animation {

    /**
    * `Animation`'s id.
    */
    private String id;

    /**
    * `Animation`'s name.
    */
    private String name;

    /**
    * `Animation`'s internal paused state.
    */
    private Boolean pausedState;

    /**
    * `Animation`'s play state.
    */
    private String playState;

    /**
    * `Animation`'s playback rate.
    */
    private Double playbackRate;

    /**
    * `Animation`'s start time.
    */
    private Double startTime;

    /**
    * `Animation`'s current time.
    */
    private Double currentTime;

    /**
    * Animation type of `Animation`.
    */
    private String type;

    /**
    * `Animation`'s source animation node.
    */
    private jpuppeteer.cdp.cdp.entity.animation.AnimationEffect source;

    /**
    * A unique ID for `Animation` representing the sources that triggered this CSS animation/transition.
    */
    private String cssId;



}
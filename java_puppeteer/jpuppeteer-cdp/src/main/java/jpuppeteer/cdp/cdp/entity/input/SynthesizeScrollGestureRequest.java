package jpuppeteer.cdp.cdp.entity.input;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SynthesizeScrollGestureRequest {

    /**
    * X coordinate of the start of the gesture in CSS pixels.
    */
    private Double x;

    /**
    * Y coordinate of the start of the gesture in CSS pixels.
    */
    private Double y;

    /**
    * The distance to scroll along the X axis (positive to scroll left).
    */
    private Double xDistance;

    /**
    * The distance to scroll along the Y axis (positive to scroll up).
    */
    private Double yDistance;

    /**
    * The number of additional pixels to scroll back along the X axis, in addition to the given distance.
    */
    private Double xOverscroll;

    /**
    * The number of additional pixels to scroll back along the Y axis, in addition to the given distance.
    */
    private Double yOverscroll;

    /**
    * Prevent fling (default: true).
    */
    private Boolean preventFling;

    /**
    * Swipe speed in pixels per second (default: 800).
    */
    private Integer speed;

    /**
    * Which type of input events to be generated (default: 'default', which queries the platform for the preferred input type).
    */
    private String gestureSourceType;

    /**
    * The number of times to repeat the gesture (default: 0).
    */
    private Integer repeatCount;

    /**
    * The number of milliseconds delay between each repeat. (default: 250).
    */
    private Integer repeatDelayMs;

    /**
    * The name of the interaction markers to generate, if not empty (default: "").
    */
    private String interactionMarkerName;



}
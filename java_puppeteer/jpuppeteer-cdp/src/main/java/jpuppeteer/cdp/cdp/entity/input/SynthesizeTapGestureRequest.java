package jpuppeteer.cdp.cdp.entity.input;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SynthesizeTapGestureRequest {

    /**
    * X coordinate of the start of the gesture in CSS pixels.
    */
    private Double x;

    /**
    * Y coordinate of the start of the gesture in CSS pixels.
    */
    private Double y;

    /**
    * Duration between touchdown and touchup events in ms (default: 50).
    */
    private Integer duration;

    /**
    * Number of times to perform the tap (e.g. 2 for double tap, default: 1).
    */
    private Integer tapCount;

    /**
    * Which type of input events to be generated (default: 'default', which queries the platform for the preferred input type).
    */
    private String gestureSourceType;



}
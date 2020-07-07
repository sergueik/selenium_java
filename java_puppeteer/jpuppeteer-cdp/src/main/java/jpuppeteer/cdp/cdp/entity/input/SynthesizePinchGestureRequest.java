package jpuppeteer.cdp.cdp.entity.input;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SynthesizePinchGestureRequest {

    /**
    * X coordinate of the start of the gesture in CSS pixels.
    */
    private Double x;

    /**
    * Y coordinate of the start of the gesture in CSS pixels.
    */
    private Double y;

    /**
    * Relative scale factor after zooming (>1.0 zooms in, <1.0 zooms out).
    */
    private Double scaleFactor;

    /**
    * Relative pointer speed in pixels per second (default: 800).
    */
    private Integer relativeSpeed;

    /**
    * Which type of input events to be generated (default: 'default', which queries the platform for the preferred input type).
    */
    private String gestureSourceType;



}
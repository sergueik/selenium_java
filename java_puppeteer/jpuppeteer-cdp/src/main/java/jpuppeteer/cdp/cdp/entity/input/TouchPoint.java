package jpuppeteer.cdp.cdp.entity.input;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TouchPoint {

    /**
    * X coordinate of the event relative to the main frame's viewport in CSS pixels.
    */
    private Double x;

    /**
    * Y coordinate of the event relative to the main frame's viewport in CSS pixels. 0 refers to the top of the viewport and Y increases as it proceeds towards the bottom of the viewport.
    */
    private Double y;

    /**
    * X radius of the touch area (default: 1.0).
    */
    private Double radiusX;

    /**
    * Y radius of the touch area (default: 1.0).
    */
    private Double radiusY;

    /**
    * Rotation angle (default: 0.0).
    */
    private Double rotationAngle;

    /**
    * Force (default: 1.0).
    */
    private Double force;

    /**
    * Identifier used to track touch sources between events, must be unique within an event.
    */
    private Double id;



}
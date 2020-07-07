package jpuppeteer.cdp.cdp.entity.input;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DispatchMouseEventRequest {

    /**
    * Type of the mouse event.
    */
    private String type;

    /**
    * X coordinate of the event relative to the main frame's viewport in CSS pixels.
    */
    private Double x;

    /**
    * Y coordinate of the event relative to the main frame's viewport in CSS pixels. 0 refers to the top of the viewport and Y increases as it proceeds towards the bottom of the viewport.
    */
    private Double y;

    /**
    * Bit field representing pressed modifier keys. Alt=1, Ctrl=2, Meta/Command=4, Shift=8 (default: 0).
    */
    private Integer modifiers;

    /**
    * Time at which the event occurred.
    */
    private Double timestamp;

    /**
    * Mouse button (default: "none").
    */
    private String button;

    /**
    * A number indicating which buttons are pressed on the mouse when a mouse event is triggered. Left=1, Right=2, Middle=4, Back=8, Forward=16, None=0.
    */
    private Integer buttons;

    /**
    * Number of times the mouse button was clicked (default: 0).
    */
    private Integer clickCount;

    /**
    * X delta in CSS pixels for mouse wheel event (default: 0).
    */
    private Double deltaX;

    /**
    * Y delta in CSS pixels for mouse wheel event (default: 0).
    */
    private Double deltaY;

    /**
    * Pointer type (default: "mouse").
    */
    private String pointerType;



}
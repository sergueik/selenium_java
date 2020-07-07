package jpuppeteer.cdp.cdp.entity.input;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EmulateTouchFromMouseEventRequest {

    /**
    * Type of the mouse event.
    */
    private String type;

    /**
    * X coordinate of the mouse pointer in DIP.
    */
    private Integer x;

    /**
    * Y coordinate of the mouse pointer in DIP.
    */
    private Integer y;

    /**
    * Mouse button. Only "none", "left", "right" are supported.
    */
    private String button;

    /**
    * Time at which the event occurred (default: current time).
    */
    private Double timestamp;

    /**
    * X delta in DIP for mouse wheel event (default: 0).
    */
    private Double deltaX;

    /**
    * Y delta in DIP for mouse wheel event (default: 0).
    */
    private Double deltaY;

    /**
    * Bit field representing pressed modifier keys. Alt=1, Ctrl=2, Meta/Command=4, Shift=8 (default: 0).
    */
    private Integer modifiers;

    /**
    * Number of times the mouse button was clicked (default: 0).
    */
    private Integer clickCount;



}
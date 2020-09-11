package jpuppeteer.cdp.cdp.entity.emulation;

/**
* Screen orientation.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScreenOrientation {

    /**
    * Orientation type.
    */
    private String type;

    /**
    * Orientation angle.
    */
    private Integer angle;



}
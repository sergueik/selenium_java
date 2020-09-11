package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetDefaultBackgroundColorOverrideRequest {

    /**
    * RGBA of the default background color. If not specified, any existing override will be cleared.
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA color;



}
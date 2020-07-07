package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetTouchEmulationEnabledRequest {

    /**
    * Whether the touch event emulation should be enabled.
    */
    private Boolean enabled;

    /**
    * Touch/gesture events configuration. Default: current platform.
    */
    private String configuration;



}
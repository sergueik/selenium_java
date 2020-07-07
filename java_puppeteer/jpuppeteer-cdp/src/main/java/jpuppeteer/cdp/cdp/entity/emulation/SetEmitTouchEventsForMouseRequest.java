package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetEmitTouchEventsForMouseRequest {

    /**
    * Whether touch emulation based on mouse input should be enabled.
    */
    private Boolean enabled;

    /**
    * Touch/gesture events configuration. Default: current platform.
    */
    private String configuration;



}
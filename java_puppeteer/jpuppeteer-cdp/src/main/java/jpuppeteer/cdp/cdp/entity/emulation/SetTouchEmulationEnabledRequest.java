package jpuppeteer.cdp.cdp.entity.emulation;

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
    * Maximum touch points supported. Defaults to one.
    */
    private Integer maxTouchPoints;



}
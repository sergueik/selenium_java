package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetVirtualTimePolicyResponse {

    /**
    * Absolute timestamp at which virtual time was first enabled (up time in milliseconds).
    */
    private Double virtualTimeTicksBase;



}
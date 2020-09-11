package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetUserAgentOverrideRequest {

    /**
    * User agent to use.
    */
    private String userAgent;

    /**
    * Browser langugage to emulate.
    */
    private String acceptLanguage;

    /**
    * The platform navigator.platform should return.
    */
    private String platform;



}
package jpuppeteer.cdp.cdp.entity.security;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HandleCertificateErrorRequest {

    /**
    * The ID of the event.
    */
    private Integer eventId;

    /**
    * The action to take on the certificate error.
    */
    private String action;



}
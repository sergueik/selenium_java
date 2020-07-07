package jpuppeteer.cdp.cdp.entity.security;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetIgnoreCertificateErrorsRequest {

    /**
    * If true, all certificate errors will be ignored.
    */
    private Boolean ignore;



}
package jpuppeteer.cdp.cdp.entity.security;

/**
* Security state information about the page.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class VisibleSecurityState {

    /**
    * The security level of the page.
    */
    private String securityState;

    /**
    * Security state details about the page certificate.
    */
    private jpuppeteer.cdp.cdp.entity.security.CertificateSecurityState certificateSecurityState;

    /**
    * The type of Safety Tip triggered on the page. Note that this field will be set even if the Safety Tip UI was not actually shown.
    */
    private jpuppeteer.cdp.cdp.entity.security.SafetyTipInfo safetyTipInfo;

    /**
    * Array of security state issues ids.
    */
    private java.util.List<String> securityStateIssueIds;



}
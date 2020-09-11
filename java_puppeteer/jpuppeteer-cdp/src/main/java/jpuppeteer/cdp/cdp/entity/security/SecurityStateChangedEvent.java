package jpuppeteer.cdp.cdp.entity.security;

/**
* The security state of the page changed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SecurityStateChangedEvent {

    /**
    * Security state.
    */
    private String securityState;

    /**
    * True if the page was loaded over cryptographic transport such as HTTPS.
    */
    private Boolean schemeIsCryptographic;

    /**
    * List of explanations for the security state. If the overall security state is `insecure` or `warning`, at least one corresponding explanation should be included.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.security.SecurityStateExplanation> explanations;

    /**
    * Information about insecure content on the page.
    */
    private jpuppeteer.cdp.cdp.entity.security.InsecureContentStatus insecureContentStatus;

    /**
    * Overrides user-visible description of the state.
    */
    private String summary;



}
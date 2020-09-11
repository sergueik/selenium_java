package jpuppeteer.cdp.cdp.entity.security;

/**
* An explanation of an factor contributing to the security state.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SecurityStateExplanation {

    /**
    * Security state representing the severity of the factor being explained.
    */
    private String securityState;

    /**
    * Title describing the type of factor.
    */
    private String title;

    /**
    * Short phrase describing the type of factor.
    */
    private String summary;

    /**
    * Full text explanation of the factor.
    */
    private String description;

    /**
    * The type of mixed content described by the explanation.
    */
    private String mixedContentType;

    /**
    * Page certificate.
    */
    private java.util.List<String> certificate;

    /**
    * Recommendations to fix any issues.
    */
    private java.util.List<String> recommendations;



}
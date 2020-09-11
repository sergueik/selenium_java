package jpuppeteer.cdp.cdp.entity.security;

/**
* Information about insecure content on the page.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class InsecureContentStatus {

    /**
    * Always false.
    */
    private Boolean ranMixedContent;

    /**
    * Always false.
    */
    private Boolean displayedMixedContent;

    /**
    * Always false.
    */
    private Boolean containedMixedForm;

    /**
    * Always false.
    */
    private Boolean ranContentWithCertErrors;

    /**
    * Always false.
    */
    private Boolean displayedContentWithCertErrors;

    /**
    * Always set to unknown.
    */
    private String ranInsecureContentStyle;

    /**
    * Always set to unknown.
    */
    private String displayedInsecureContentStyle;



}
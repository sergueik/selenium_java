package jpuppeteer.cdp.cdp.entity.security;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SafetyTipInfo {

    /**
    * Describes whether the page triggers any safety tips or reputation warnings. Default is unknown.
    */
    private String safetyTipStatus;

    /**
    * The URL the safety tip suggested ("Did you mean?"). Only filled in for lookalike matches.
    */
    private String safeUrl;



}
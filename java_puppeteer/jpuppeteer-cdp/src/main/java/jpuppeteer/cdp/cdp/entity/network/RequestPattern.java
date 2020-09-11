package jpuppeteer.cdp.cdp.entity.network;

/**
* Request pattern for interception.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestPattern {

    /**
    * Wildcards ('*' -> zero or more, '?' -> exactly one) are allowed. Escape character is backslash. Omitting is equivalent to "*".
    */
    private String urlPattern;

    /**
    * If set, only requests for matching resource types will be intercepted.
    */
    private String resourceType;

    /**
    * Stage at wich to begin intercepting requests. Default is Request.
    */
    private String interceptionStage;



}
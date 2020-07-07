package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetVersionResponse {

    /**
    * Protocol version.
    */
    private String protocolVersion;

    /**
    * Product name.
    */
    private String product;

    /**
    * Product revision.
    */
    private String revision;

    /**
    * User-Agent.
    */
    private String userAgent;

    /**
    * V8 version.
    */
    private String jsVersion;



}
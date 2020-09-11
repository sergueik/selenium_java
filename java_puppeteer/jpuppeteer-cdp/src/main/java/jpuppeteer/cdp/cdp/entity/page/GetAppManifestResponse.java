package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetAppManifestResponse {

    /**
    * Manifest location.
    */
    private String url;

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.page.AppManifestError> errors;

    /**
    * Manifest content.
    */
    private String data;

    /**
    * Parsed manifest properties
    */
    private jpuppeteer.cdp.cdp.entity.page.AppManifestParsedProperties parsed;



}
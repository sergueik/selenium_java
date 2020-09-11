package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AddCompilationCacheRequest {

    /**
    */
    private String url;

    /**
    * Base64-encoded data
    */
    private String data;



}
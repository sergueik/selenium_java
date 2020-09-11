package jpuppeteer.cdp.cdp.entity.page;

/**
* Issued for every compilation cache generated. Is only available if Page.setGenerateCompilationCache is enabled.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CompilationCacheProducedEvent {

    /**
    */
    private String url;

    /**
    * Base64-encoded data
    */
    private String data;



}
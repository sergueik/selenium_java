package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetDownloadBehaviorRequest {

    /**
    * Whether to allow all or deny all download requests, or use default Chrome behavior if available (otherwise deny).
    */
    private String behavior;

    /**
    * The default path to save downloaded files to. This is requred if behavior is set to 'allow'
    */
    private String downloadPath;



}
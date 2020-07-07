package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when page is about to start a download.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DownloadWillBeginEvent {

    /**
    * Id of the frame that caused download to begin.
    */
    private String frameId;

    /**
    * URL of the resource being downloaded.
    */
    private String url;



}
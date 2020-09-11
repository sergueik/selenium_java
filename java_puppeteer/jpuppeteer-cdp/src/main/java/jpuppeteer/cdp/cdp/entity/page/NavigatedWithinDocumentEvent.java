package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when same-document navigation happens, e.g. due to history API usage or anchor navigation.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NavigatedWithinDocumentEvent {

    /**
    * Id of the frame.
    */
    private String frameId;

    /**
    * Frame's new url.
    */
    private String url;



}
package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ApplicationCacheStatusUpdatedEvent {

    /**
    * Identifier of the frame containing document whose application cache updated status.
    */
    private String frameId;

    /**
    * Manifest URL.
    */
    private String manifestURL;

    /**
    * Updated application cache status.
    */
    private Integer status;



}
package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* Frame identifier - manifest URL pair.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameWithManifest {

    /**
    * Frame identifier.
    */
    private String frameId;

    /**
    * Manifest URL.
    */
    private String manifestURL;

    /**
    * Application cache status.
    */
    private Integer status;



}
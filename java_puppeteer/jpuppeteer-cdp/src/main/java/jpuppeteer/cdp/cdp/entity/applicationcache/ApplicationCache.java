package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* Detailed application cache information.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ApplicationCache {

    /**
    * Manifest URL.
    */
    private String manifestURL;

    /**
    * Application cache size.
    */
    private Double size;

    /**
    * Application cache creation time.
    */
    private Double creationTime;

    /**
    * Application cache update time.
    */
    private Double updateTime;

    /**
    * Application cache resources.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.applicationcache.ApplicationCacheResource> resources;



}
package jpuppeteer.cdp.cdp.entity.applicationcache;

/**
* Detailed application cache resource information.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ApplicationCacheResource {

    /**
    * Resource url.
    */
    private String url;

    /**
    * Resource size.
    */
    private Integer size;

    /**
    * Resource type.
    */
    private String type;



}
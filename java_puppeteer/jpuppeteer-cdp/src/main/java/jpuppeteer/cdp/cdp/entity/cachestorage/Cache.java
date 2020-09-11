package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* Cache identifier.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Cache {

    /**
    * An opaque unique id of the cache.
    */
    private String cacheId;

    /**
    * Security origin of the cache.
    */
    private String securityOrigin;

    /**
    * The name of the cache.
    */
    private String cacheName;



}
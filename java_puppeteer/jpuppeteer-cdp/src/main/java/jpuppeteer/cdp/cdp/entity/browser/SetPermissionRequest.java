package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetPermissionRequest {

    /**
    * Origin the permission applies to.
    */
    private String origin;

    /**
    * Descriptor of permission to override.
    */
    private jpuppeteer.cdp.cdp.entity.browser.PermissionDescriptor permission;

    /**
    * Setting of the permission.
    */
    private String setting;

    /**
    * Context to override. When omitted, default browser context is used.
    */
    private String browserContextId;



}
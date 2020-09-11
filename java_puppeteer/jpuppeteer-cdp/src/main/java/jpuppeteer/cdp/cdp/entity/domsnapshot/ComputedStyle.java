package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* A subset of the full ComputedStyle as defined by the request whitelist.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ComputedStyle {

    /**
    * Name/value pairs of computed style properties.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.domsnapshot.NameValue> properties;



}
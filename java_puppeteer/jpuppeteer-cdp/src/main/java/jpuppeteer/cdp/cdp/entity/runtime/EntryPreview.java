package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EntryPreview {

    /**
    * Preview of the key. Specified for map-like collection entries.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ObjectPreview key;

    /**
    * Preview of the value.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.ObjectPreview value;



}
package jpuppeteer.cdp.cdp.entity.css;

/**
* Fired whenever an active document stylesheet is added.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StyleSheetAddedEvent {

    /**
    * Added stylesheet metainfo.
    */
    private jpuppeteer.cdp.cdp.entity.css.CSSStyleSheetHeader header;



}
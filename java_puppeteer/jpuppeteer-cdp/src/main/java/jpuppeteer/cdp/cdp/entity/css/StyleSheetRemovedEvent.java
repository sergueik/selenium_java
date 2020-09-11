package jpuppeteer.cdp.cdp.entity.css;

/**
* Fired whenever an active document stylesheet is removed.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StyleSheetRemovedEvent {

    /**
    * Identifier of the removed stylesheet.
    */
    private String styleSheetId;



}
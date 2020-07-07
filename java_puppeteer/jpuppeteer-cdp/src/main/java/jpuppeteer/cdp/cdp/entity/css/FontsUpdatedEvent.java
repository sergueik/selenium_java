package jpuppeteer.cdp.cdp.entity.css;

/**
* Fires whenever a web font is updated.  A non-empty font parameter indicates a successfully loaded web font
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FontsUpdatedEvent {

    /**
    * The web font that has loaded.
    */
    private jpuppeteer.cdp.cdp.entity.css.FontFace font;



}
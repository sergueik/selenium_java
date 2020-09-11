package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetPlatformFontsForNodeResponse {

    /**
    * Usage statistics for every employed platform font.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.PlatformFontUsage> fonts;



}
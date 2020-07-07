package jpuppeteer.cdp.cdp.entity.css;

/**
* Information about amount of glyphs that were rendered with given font.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PlatformFontUsage {

    /**
    * Font's family name reported by platform.
    */
    private String familyName;

    /**
    * Indicates if the font was downloaded or resolved locally.
    */
    private Boolean isCustomFont;

    /**
    * Amount of glyphs that were rendered with this font.
    */
    private Double glyphCount;



}
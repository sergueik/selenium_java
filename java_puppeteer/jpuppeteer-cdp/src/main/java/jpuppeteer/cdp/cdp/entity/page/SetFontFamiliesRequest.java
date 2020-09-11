package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetFontFamiliesRequest {

    /**
    * Specifies font families to set. If a font family is not specified, it won't be changed.
    */
    private jpuppeteer.cdp.cdp.entity.page.FontFamilies fontFamilies;



}
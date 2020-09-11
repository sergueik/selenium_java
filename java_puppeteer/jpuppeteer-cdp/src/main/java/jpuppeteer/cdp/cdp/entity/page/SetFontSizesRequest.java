package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetFontSizesRequest {

    /**
    * Specifies font sizes to set. If a font size is not specified, it won't be changed.
    */
    private jpuppeteer.cdp.cdp.entity.page.FontSizes fontSizes;



}
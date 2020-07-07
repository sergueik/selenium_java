package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetKeyframeKeyRequest {

    /**
    */
    private String styleSheetId;

    /**
    */
    private jpuppeteer.cdp.cdp.entity.css.SourceRange range;

    /**
    */
    private String keyText;



}
package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetDockTileRequest {

    /**
    */
    private String badgeLabel;

    /**
    * Png encoded image.
    */
    private String image;



}
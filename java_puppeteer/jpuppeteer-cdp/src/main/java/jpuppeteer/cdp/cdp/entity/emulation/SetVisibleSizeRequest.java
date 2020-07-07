package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetVisibleSizeRequest {

    /**
    * Frame width (DIP).
    */
    private Integer width;

    /**
    * Frame height (DIP).
    */
    private Integer height;



}
package jpuppeteer.cdp.cdp.entity.page;

/**
* Screencast frame metadata.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScreencastFrameMetadata {

    /**
    * Top offset in DIP.
    */
    private Double offsetTop;

    /**
    * Page scale factor.
    */
    private Double pageScaleFactor;

    /**
    * Device screen width in DIP.
    */
    private Double deviceWidth;

    /**
    * Device screen height in DIP.
    */
    private Double deviceHeight;

    /**
    * Position of horizontal scroll in CSS pixels.
    */
    private Double scrollOffsetX;

    /**
    * Position of vertical scroll in CSS pixels.
    */
    private Double scrollOffsetY;

    /**
    * Frame swap timestamp.
    */
    private Double timestamp;



}
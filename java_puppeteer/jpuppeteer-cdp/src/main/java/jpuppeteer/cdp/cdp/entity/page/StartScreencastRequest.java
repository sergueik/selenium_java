package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StartScreencastRequest {

    /**
    * Image compression format.
    */
    private String format;

    /**
    * Compression quality from range [0..100].
    */
    private Integer quality;

    /**
    * Maximum screenshot width.
    */
    private Integer maxWidth;

    /**
    * Maximum screenshot height.
    */
    private Integer maxHeight;

    /**
    * Send every n-th frame.
    */
    private Integer everyNthFrame;



}
package jpuppeteer.cdp.cdp.entity.page;

/**
* Compressed image data requested by the `startScreencast`.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScreencastFrameEvent {

    /**
    * Base64-encoded compressed image.
    */
    private String data;

    /**
    * Screencast frame metadata.
    */
    private jpuppeteer.cdp.cdp.entity.page.ScreencastFrameMetadata metadata;

    /**
    * Frame number.
    */
    private Integer sessionId;



}
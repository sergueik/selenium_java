package jpuppeteer.cdp.cdp.entity.io;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CloseRequest {

    /**
    * Handle of the stream to close.
    */
    private String handle;



}
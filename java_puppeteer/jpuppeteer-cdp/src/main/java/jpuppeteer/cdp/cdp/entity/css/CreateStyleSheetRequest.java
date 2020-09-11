package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CreateStyleSheetRequest {

    /**
    * Identifier of the frame where "via-inspector" stylesheet should be created.
    */
    private String frameId;



}
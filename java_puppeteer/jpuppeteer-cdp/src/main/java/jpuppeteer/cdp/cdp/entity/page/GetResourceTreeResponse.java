package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetResourceTreeResponse {

    /**
    * Present frame / resource tree structure.
    */
    private jpuppeteer.cdp.cdp.entity.page.FrameResourceTree frameTree;



}
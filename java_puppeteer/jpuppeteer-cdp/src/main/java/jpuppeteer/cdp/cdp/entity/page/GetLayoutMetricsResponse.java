package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetLayoutMetricsResponse {

    /**
    * Metrics relating to the layout viewport.
    */
    private jpuppeteer.cdp.cdp.entity.page.LayoutViewport layoutViewport;

    /**
    * Metrics relating to the visual viewport.
    */
    private jpuppeteer.cdp.cdp.entity.page.VisualViewport visualViewport;

    /**
    * Size of scrollable area.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect contentSize;



}
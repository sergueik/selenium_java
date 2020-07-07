package jpuppeteer.cdp.cdp.entity.overlay;

/**
* Configuration data for the highlighting of page elements.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HighlightConfig {

    /**
    * Whether the node info tooltip should be shown (default: false).
    */
    private Boolean showInfo;

    /**
    * Whether the node styles in the tooltip (default: false).
    */
    private Boolean showStyles;

    /**
    * Whether the rulers should be shown (default: false).
    */
    private Boolean showRulers;

    /**
    * Whether the extension lines from node to the rulers should be shown (default: false).
    */
    private Boolean showExtensionLines;

    /**
    * The content box highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA contentColor;

    /**
    * The padding highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA paddingColor;

    /**
    * The border highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA borderColor;

    /**
    * The margin highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA marginColor;

    /**
    * The event target element highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA eventTargetColor;

    /**
    * The shape outside fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA shapeColor;

    /**
    * The shape margin fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA shapeMarginColor;

    /**
    * The grid layout color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA cssGridColor;



}
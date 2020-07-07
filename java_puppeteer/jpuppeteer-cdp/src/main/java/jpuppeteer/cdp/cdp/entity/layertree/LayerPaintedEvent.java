package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LayerPaintedEvent {

    /**
    * The id of the painted layer.
    */
    private String layerId;

    /**
    * Clip rectangle.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect clip;



}
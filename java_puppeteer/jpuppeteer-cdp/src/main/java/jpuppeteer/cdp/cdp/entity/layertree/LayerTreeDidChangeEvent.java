package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LayerTreeDidChangeEvent {

    /**
    * Layer tree, absent if not in the comspositing mode.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.layertree.Layer> layers;



}
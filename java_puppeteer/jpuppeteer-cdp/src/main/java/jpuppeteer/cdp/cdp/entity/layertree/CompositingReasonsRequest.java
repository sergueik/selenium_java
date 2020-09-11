package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CompositingReasonsRequest {

    /**
    * The id of the layer for which we want to get the reasons it was composited.
    */
    private String layerId;



}
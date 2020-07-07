package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetContentQuadsResponse {

    /**
    * Quads that describe node layout relative to viewport.
    */
    private java.util.List<java.util.List<Double>> quads;



}
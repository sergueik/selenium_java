package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CompositingReasonsResponse {

    /**
    * A list of strings specifying reasons for the given layer to become composited.
    */
    private java.util.List<String> compositingReasons;



}
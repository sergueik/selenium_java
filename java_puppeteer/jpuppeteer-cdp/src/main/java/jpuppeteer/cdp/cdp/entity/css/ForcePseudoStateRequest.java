package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ForcePseudoStateRequest {

    /**
    * The element id for which to force the pseudo state.
    */
    private Integer nodeId;

    /**
    * Element pseudo classes to force when computing the element's style.
    */
    private java.util.List<String> forcedPseudoClasses;



}
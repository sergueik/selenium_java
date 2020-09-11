package jpuppeteer.cdp.cdp.entity.memory;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetDOMCountersResponse {

    /**
    */
    private Integer documents;

    /**
    */
    private Integer nodes;

    /**
    */
    private Integer jsEventListeners;



}
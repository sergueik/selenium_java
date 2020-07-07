package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetDataSizeLimitsForTestRequest {

    /**
    * Maximum total buffer size.
    */
    private Integer maxTotalSize;

    /**
    * Maximum per-resource size.
    */
    private Integer maxResourceSize;



}
package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetAsyncCallStackDepthRequest {

    /**
    * Maximum depth of async call stacks. Setting to `0` will effectively disable collecting async call stacks (default).
    */
    private Integer maxDepth;



}
package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Description of an isolated world.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExecutionContextDescription {

    /**
    * Unique id of the execution context. It can be used to specify in which execution context script evaluation should be performed.
    */
    private Integer id;

    /**
    * Execution context origin.
    */
    private String origin;

    /**
    * Human readable name describing given context.
    */
    private String name;

    /**
    * Embedder-specific auxiliary data.
    */
    private java.util.Map<String, Object> auxData;



}
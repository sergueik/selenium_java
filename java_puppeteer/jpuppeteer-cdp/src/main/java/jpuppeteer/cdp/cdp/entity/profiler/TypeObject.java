package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Describes a type collected during runtime.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TypeObject {

    /**
    * Name of a type collected with type profiling.
    */
    private String name;



}
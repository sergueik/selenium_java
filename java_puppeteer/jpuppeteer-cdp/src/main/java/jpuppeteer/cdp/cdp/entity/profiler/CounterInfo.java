package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Collected counter information.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CounterInfo {

    /**
    * Counter name.
    */
    private String name;

    /**
    * Counter value.
    */
    private Integer value;



}
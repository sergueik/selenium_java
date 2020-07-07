package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* Represents process info.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ProcessInfo {

    /**
    * Specifies process type.
    */
    private String type;

    /**
    * Specifies process id.
    */
    private Integer id;

    /**
    * Specifies cumulative CPU usage in seconds across all threads of the process since the process start.
    */
    private Double cpuTime;



}
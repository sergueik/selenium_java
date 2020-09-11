package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Specifies a number of samples attributed to a certain source position.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PositionTickInfo {

    /**
    * Source line number (1-based).
    */
    private Integer line;

    /**
    * Number of samples attributed to the source line.
    */
    private Integer ticks;



}
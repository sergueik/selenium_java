package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Coverage data for a source range.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CoverageRange {

    /**
    * JavaScript script source offset for the range start.
    */
    private Integer startOffset;

    /**
    * JavaScript script source offset for the range end.
    */
    private Integer endOffset;

    /**
    * Collected execution count of the source range.
    */
    private Integer count;



}
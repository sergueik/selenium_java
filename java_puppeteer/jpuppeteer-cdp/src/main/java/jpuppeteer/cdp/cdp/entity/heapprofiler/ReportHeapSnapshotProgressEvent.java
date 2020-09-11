package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ReportHeapSnapshotProgressEvent {

    /**
    */
    private Integer done;

    /**
    */
    private Integer total;

    /**
    */
    private Boolean finished;



}
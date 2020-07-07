package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ProfileSnapshotRequest {

    /**
    * The id of the layer snapshot.
    */
    private String snapshotId;

    /**
    * The maximum number of times to replay the snapshot (1, if not specified).
    */
    private Integer minRepeatCount;

    /**
    * The minimum duration (in seconds) to replay the snapshot.
    */
    private Double minDuration;

    /**
    * The clip rectangle to apply when replaying the snapshot.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect clipRect;



}
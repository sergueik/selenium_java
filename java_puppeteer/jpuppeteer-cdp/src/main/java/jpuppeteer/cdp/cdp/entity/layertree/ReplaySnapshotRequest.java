package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ReplaySnapshotRequest {

    /**
    * The id of the layer snapshot.
    */
    private String snapshotId;

    /**
    * The first step to replay from (replay from the very start if not specified).
    */
    private Integer fromStep;

    /**
    * The last step to replay to (replay till the end if not specified).
    */
    private Integer toStep;

    /**
    * The scale to apply while replaying (defaults to 1).
    */
    private Double scale;



}
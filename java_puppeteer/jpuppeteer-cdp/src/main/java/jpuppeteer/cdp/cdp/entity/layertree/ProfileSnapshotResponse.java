package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ProfileSnapshotResponse {

    /**
    * The array of paint profiles, one per run.
    */
    private java.util.List<java.util.List<Double>> timings;



}
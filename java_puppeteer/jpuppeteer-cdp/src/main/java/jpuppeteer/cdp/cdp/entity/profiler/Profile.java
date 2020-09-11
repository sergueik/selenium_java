package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Profile.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Profile {

    /**
    * The list of profile nodes. First item is the root node.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.ProfileNode> nodes;

    /**
    * Profiling start timestamp in microseconds.
    */
    private Double startTime;

    /**
    * Profiling end timestamp in microseconds.
    */
    private Double endTime;

    /**
    * Ids of samples top nodes.
    */
    private java.util.List<Integer> samples;

    /**
    * Time intervals between adjacent samples in microseconds. The first delta is relative to the profile startTime.
    */
    private java.util.List<Integer> timeDeltas;



}
package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* If heap objects tracking has been started then backend may send update for one or more fragments
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HeapStatsUpdateEvent {

    /**
    * An array of triplets. Each triplet describes a fragment. The first integer is the fragment index, the second integer is a total count of objects for the fragment, the third integer is a total size of the objects for the fragment.
    */
    private java.util.List<Integer> statsUpdate;



}
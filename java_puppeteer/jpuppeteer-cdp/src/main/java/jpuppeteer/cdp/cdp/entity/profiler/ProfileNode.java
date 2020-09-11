package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Profile node. Holds callsite information, execution statistics and child nodes.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ProfileNode {

    /**
    * Unique id of the node.
    */
    private Integer id;

    /**
    * Function location.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.CallFrame callFrame;

    /**
    * Number of samples where this node was on top of the call stack.
    */
    private Integer hitCount;

    /**
    * Child node ids.
    */
    private java.util.List<Integer> children;

    /**
    * The reason of being not optimized. The function may be deoptimized or marked as don't optimize.
    */
    private String deoptReason;

    /**
    * An array of source position ticks.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.PositionTickInfo> positionTicks;



}
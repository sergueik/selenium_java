package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetVirtualTimePolicyRequest {

    /**
    */
    private String policy;

    /**
    * If set, after this many virtual milliseconds have elapsed virtual time will be paused and a virtualTimeBudgetExpired event is sent.
    */
    private Double budget;

    /**
    * If set this specifies the maximum number of tasks that can be run before virtual is forced forwards to prevent deadlock.
    */
    private Integer maxVirtualTimeTaskStarvationCount;

    /**
    * If set the virtual time policy change should be deferred until any frame starts navigating. Note any previous deferred policy change is superseded.
    */
    private Boolean waitForNavigation;

    /**
    * If set, base::Time::Now will be overriden to initially return this value.
    */
    private Double initialVirtualTime;



}
package jpuppeteer.cdp.cdp.entity.profiler;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ConsoleProfileFinishedEvent {

    /**
    */
    private String id;

    /**
    * Location of console.profileEnd().
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location location;

    /**
    */
    private jpuppeteer.cdp.cdp.entity.profiler.Profile profile;

    /**
    * Profile title passed as an argument to console.profile().
    */
    private String title;



}
package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Sent when new profile recording is started using console.profile() call.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ConsoleProfileStartedEvent {

    /**
    */
    private String id;

    /**
    * Location of console.profile().
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location location;

    /**
    * Profile title passed as an argument to console.profile().
    */
    private String title;



}
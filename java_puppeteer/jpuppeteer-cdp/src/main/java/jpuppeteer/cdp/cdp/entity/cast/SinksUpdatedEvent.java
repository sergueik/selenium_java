package jpuppeteer.cdp.cdp.entity.cast;

/**
* This is fired whenever the list of available sinks changes. A sink is a device or a software surface that you can cast to.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SinksUpdatedEvent {

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.cast.Sink> sinks;



}
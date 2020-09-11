package jpuppeteer.cdp.cdp.entity.console;

/**
* Issued when new console message is added.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class MessageAddedEvent {

    /**
    * Console message that has been added.
    */
    private jpuppeteer.cdp.cdp.entity.console.ConsoleMessage message;



}
package jpuppeteer.cdp.cdp.entity.log;

/**
* Issued when new message was logged.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EntryAddedEvent {

    /**
    * The entry.
    */
    private jpuppeteer.cdp.cdp.entity.log.LogEntry entry;



}
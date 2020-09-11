package jpuppeteer.cdp.cdp.entity.console;

/**
* Console message.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ConsoleMessage {

    /**
    * Message source.
    */
    private String source;

    /**
    * Message severity.
    */
    private String level;

    /**
    * Message text.
    */
    private String text;

    /**
    * URL of the message origin.
    */
    private String url;

    /**
    * Line number in the resource that generated this message (1-based).
    */
    private Integer line;

    /**
    * Column number in the resource that generated this message (1-based).
    */
    private Integer column;



}
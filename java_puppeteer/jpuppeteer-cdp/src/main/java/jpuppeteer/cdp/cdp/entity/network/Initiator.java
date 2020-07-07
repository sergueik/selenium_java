package jpuppeteer.cdp.cdp.entity.network;

/**
* Information about the request initiator.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Initiator {

    /**
    * Type of this initiator.
    */
    private String type;

    /**
    * Initiator JavaScript stack trace, set for Script only.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace stack;

    /**
    * Initiator URL, set for Parser type or for Script type (when script is importing module) or for SignedExchange type.
    */
    private String url;

    /**
    * Initiator line number, set for Parser type or for Script type (when script is importing module) (0-based).
    */
    private Double lineNumber;



}
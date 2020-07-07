package jpuppeteer.cdp.cdp.entity.cast;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Sink {

    /**
    */
    private String name;

    /**
    */
    private String id;

    /**
    * Text describing the current session. Present only if there is an active session on the sink.
    */
    private String session;



}
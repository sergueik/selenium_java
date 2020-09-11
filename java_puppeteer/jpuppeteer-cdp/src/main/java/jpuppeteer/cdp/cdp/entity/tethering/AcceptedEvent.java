package jpuppeteer.cdp.cdp.entity.tethering;

/**
* Informs that port was successfully bound and got a specified connection id.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AcceptedEvent {

    /**
    * Port number that was successfully bound.
    */
    private Integer port;

    /**
    * Connection id to be used.
    */
    private String connectionId;



}
package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when data chunk was received over the network.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DataReceivedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * Data chunk length.
    */
    private Integer dataLength;

    /**
    * Actual bytes received (might be less than dataLength for compressed encodings).
    */
    private Integer encodedDataLength;



}
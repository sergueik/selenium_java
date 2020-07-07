package jpuppeteer.cdp.cdp.entity.network;

/**
* Timing information for the request.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ResourceTiming {

    /**
    * Timing's requestTime is a baseline in seconds, while the other numbers are ticks in milliseconds relatively to this requestTime.
    */
    private Double requestTime;

    /**
    * Started resolving proxy.
    */
    private Double proxyStart;

    /**
    * Finished resolving proxy.
    */
    private Double proxyEnd;

    /**
    * Started DNS address resolve.
    */
    private Double dnsStart;

    /**
    * Finished DNS address resolve.
    */
    private Double dnsEnd;

    /**
    * Started connecting to the remote host.
    */
    private Double connectStart;

    /**
    * Connected to the remote host.
    */
    private Double connectEnd;

    /**
    * Started SSL handshake.
    */
    private Double sslStart;

    /**
    * Finished SSL handshake.
    */
    private Double sslEnd;

    /**
    * Started running ServiceWorker.
    */
    private Double workerStart;

    /**
    * Finished Starting ServiceWorker.
    */
    private Double workerReady;

    /**
    * Started sending request.
    */
    private Double sendStart;

    /**
    * Finished sending request.
    */
    private Double sendEnd;

    /**
    * Time the server started pushing request.
    */
    private Double pushStart;

    /**
    * Time the server finished pushing request.
    */
    private Double pushEnd;

    /**
    * Finished receiving response headers.
    */
    private Double receiveHeadersEnd;



}
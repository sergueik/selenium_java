package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when additional information about a requestWillBeSent event is available from the network stack. Not every requestWillBeSent event will have an additional requestWillBeSentExtraInfo fired for it, and there is no guarantee whether requestWillBeSent or requestWillBeSentExtraInfo will be fired first for the same request.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestWillBeSentExtraInfoEvent {

    /**
    * Request identifier. Used to match this information to an existing requestWillBeSent event.
    */
    private String requestId;

    /**
    * A list of cookies which will not be sent with this request along with corresponding reasons for blocking.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.BlockedCookieWithReason> blockedCookies;

    /**
    * Raw request headers as they will be sent over the wire.
    */
    private java.util.Map<String, Object> headers;



}
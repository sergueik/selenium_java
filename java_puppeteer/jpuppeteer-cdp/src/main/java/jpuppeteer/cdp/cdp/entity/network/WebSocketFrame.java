package jpuppeteer.cdp.cdp.entity.network;

/**
* WebSocket message data. This represents an entire WebSocket message, not just a fragmented frame as the name suggests.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class WebSocketFrame {

    /**
    * WebSocket message opcode.
    */
    private Double opcode;

    /**
    * WebSocket message mask.
    */
    private Boolean mask;

    /**
    * WebSocket message payload data. If the opcode is 1, this is a text message and payloadData is a UTF-8 string. If the opcode isn't 1, then payloadData is a base64 encoded string representing binary data.
    */
    private String payloadData;



}
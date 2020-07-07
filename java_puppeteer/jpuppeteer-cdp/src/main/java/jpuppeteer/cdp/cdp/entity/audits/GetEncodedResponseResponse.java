package jpuppeteer.cdp.cdp.entity.audits;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetEncodedResponseResponse {

    /**
    * The encoded body as a base64 string. Omitted if sizeOnly is true.
    */
    private String body;

    /**
    * Size before re-encoding.
    */
    private Integer originalSize;

    /**
    * Size after re-encoding.
    */
    private Integer encodedSize;



}
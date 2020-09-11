package jpuppeteer.cdp.cdp.entity.audits;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetEncodedResponseRequest {

    /**
    * Identifier of the network request to get content for.
    */
    private String requestId;

    /**
    * The encoding to use.
    */
    private String encoding;

    /**
    * The quality of the encoding (0-1). (defaults to 1)
    */
    private Double quality;

    /**
    * Whether to only return the size information (defaults to false).
    */
    private Boolean sizeOnly;



}
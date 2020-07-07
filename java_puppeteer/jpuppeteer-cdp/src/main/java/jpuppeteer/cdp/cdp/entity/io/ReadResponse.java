package jpuppeteer.cdp.cdp.entity.io;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ReadResponse {

    /**
    * Set if the data is base64-encoded
    */
    private Boolean base64Encoded;

    /**
    * Data that were read.
    */
    private String data;

    /**
    * Set if the end-of-file condition occured while reading.
    */
    private Boolean eof;



}
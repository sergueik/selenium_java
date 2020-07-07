package jpuppeteer.cdp.cdp.entity.io;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ReadRequest {

    /**
    * Handle of the stream to read.
    */
    private String handle;

    /**
    * Seek to the specified offset before reading (if not specificed, proceed with offset following the last read). Some types of streams may only support sequential reads.
    */
    private Integer offset;

    /**
    * Maximum number of bytes to read (left upon the agent discretion if not specified).
    */
    private Integer size;



}
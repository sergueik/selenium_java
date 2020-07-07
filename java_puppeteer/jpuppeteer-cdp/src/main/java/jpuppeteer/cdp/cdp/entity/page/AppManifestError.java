package jpuppeteer.cdp.cdp.entity.page;

/**
* Error while paring app manifest.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AppManifestError {

    /**
    * Error message.
    */
    private String message;

    /**
    * If criticial, this is a non-recoverable parse error.
    */
    private Integer critical;

    /**
    * Error line.
    */
    private Integer line;

    /**
    * Error column.
    */
    private Integer column;



}
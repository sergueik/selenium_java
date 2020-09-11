package jpuppeteer.cdp.cdp.entity.database;

/**
* Database error.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Error {

    /**
    * Error message.
    */
    private String message;

    /**
    * Error code.
    */
    private Integer code;



}
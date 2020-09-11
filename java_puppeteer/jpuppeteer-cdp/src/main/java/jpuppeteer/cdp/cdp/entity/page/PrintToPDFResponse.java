package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PrintToPDFResponse {

    /**
    * Base64-encoded pdf data. Empty if |returnAsStream| is specified.
    */
    private String data;

    /**
    * A handle of the stream that holds resulting PDF data.
    */
    private String stream;



}
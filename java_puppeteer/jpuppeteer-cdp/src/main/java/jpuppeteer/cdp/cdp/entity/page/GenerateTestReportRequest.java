package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GenerateTestReportRequest {

    /**
    * Message to be displayed in the report.
    */
    private String message;

    /**
    * Specifies the endpoint group to deliver the report to.
    */
    private String group;



}
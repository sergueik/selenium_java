package jpuppeteer.cdp.cdp.entity.log;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StartViolationsReportRequest {

    /**
    * Configuration for violations.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.log.ViolationSetting> config;



}
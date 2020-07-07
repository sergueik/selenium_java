package jpuppeteer.cdp.cdp.entity.log;

/**
* Violation configuration setting.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ViolationSetting {

    /**
    * Violation type.
    */
    private String name;

    /**
    * Time threshold to trigger upon.
    */
    private Double threshold;



}
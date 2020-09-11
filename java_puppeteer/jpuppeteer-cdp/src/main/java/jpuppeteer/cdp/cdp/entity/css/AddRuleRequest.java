package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AddRuleRequest {

    /**
    * The css style sheet identifier where a new rule should be inserted.
    */
    private String styleSheetId;

    /**
    * The text of a new rule.
    */
    private String ruleText;

    /**
    * Text position of a new rule in the target style sheet.
    */
    private jpuppeteer.cdp.cdp.entity.css.SourceRange location;



}
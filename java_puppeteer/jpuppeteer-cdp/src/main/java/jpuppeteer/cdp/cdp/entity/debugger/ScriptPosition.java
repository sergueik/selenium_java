package jpuppeteer.cdp.cdp.entity.debugger;

/**
* Location in the source code.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScriptPosition {

    /**
    */
    private Integer lineNumber;

    /**
    */
    private Integer columnNumber;



}
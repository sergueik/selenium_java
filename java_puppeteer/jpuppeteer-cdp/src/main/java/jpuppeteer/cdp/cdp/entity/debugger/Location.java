package jpuppeteer.cdp.cdp.entity.debugger;

/**
* Location in the source code.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Location {

    /**
    * Script identifier as reported in the `Debugger.scriptParsed`.
    */
    private String scriptId;

    /**
    * Line number in the script (0-based).
    */
    private Integer lineNumber;

    /**
    * Column number in the script (0-based).
    */
    private Integer columnNumber;



}
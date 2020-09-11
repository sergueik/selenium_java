package jpuppeteer.cdp.cdp.entity.css;

/**
* Data for a simple selector (these are delimited by commas in a selector list).
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Value {

    /**
    * Value text.
    */
    private String text;

    /**
    * Value range in the underlying resource (if available).
    */
    private jpuppeteer.cdp.cdp.entity.css.SourceRange range;



}
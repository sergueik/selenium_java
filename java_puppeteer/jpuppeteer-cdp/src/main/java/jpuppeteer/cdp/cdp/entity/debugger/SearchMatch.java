package jpuppeteer.cdp.cdp.entity.debugger;

/**
* Search match for resource.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SearchMatch {

    /**
    * Line number in resource content.
    */
    private Double lineNumber;

    /**
    * Line with match content.
    */
    private String lineContent;



}
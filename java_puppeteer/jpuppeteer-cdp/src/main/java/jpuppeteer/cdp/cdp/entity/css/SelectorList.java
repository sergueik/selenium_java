package jpuppeteer.cdp.cdp.entity.css;

/**
* Selector list data.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SelectorList {

    /**
    * Selectors in the list.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.Value> selectors;

    /**
    * Rule selector text.
    */
    private String text;



}
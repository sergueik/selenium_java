package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CSSComputedStyleProperty {

    /**
    * Computed style property name.
    */
    private String name;

    /**
    * Computed style property value.
    */
    private String value;



}
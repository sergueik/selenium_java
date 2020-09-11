package jpuppeteer.cdp.cdp.entity.css;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ShorthandEntry {

    /**
    * Shorthand name.
    */
    private String name;

    /**
    * Shorthand value.
    */
    private String value;

    /**
    * Whether the property has "!important" annotation (implies `false` if absent).
    */
    private Boolean important;



}
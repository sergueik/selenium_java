package jpuppeteer.cdp.cdp.entity.css;

/**
* Media query expression descriptor.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class MediaQueryExpression {

    /**
    * Media query expression value.
    */
    private Double value;

    /**
    * Media query expression units.
    */
    private String unit;

    /**
    * Media query expression feature.
    */
    private String feature;

    /**
    * The associated range of the value text in the enclosing stylesheet (if available).
    */
    private jpuppeteer.cdp.cdp.entity.css.SourceRange valueRange;

    /**
    * Computed length of media query expression (if applicable).
    */
    private Double computedLength;



}
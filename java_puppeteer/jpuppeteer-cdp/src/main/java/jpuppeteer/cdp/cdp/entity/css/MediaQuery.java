package jpuppeteer.cdp.cdp.entity.css;

/**
* Media query descriptor.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class MediaQuery {

    /**
    * Array of media query expressions.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.MediaQueryExpression> expressions;

    /**
    * Whether the media query condition is satisfied.
    */
    private Boolean active;



}
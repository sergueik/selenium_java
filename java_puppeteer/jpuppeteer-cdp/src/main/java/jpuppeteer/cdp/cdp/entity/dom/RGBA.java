package jpuppeteer.cdp.cdp.entity.dom;

/**
* A structure holding an RGBA color.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RGBA {

    /**
    * The red component, in the [0-255] range.
    */
    private Integer r;

    /**
    * The green component, in the [0-255] range.
    */
    private Integer g;

    /**
    * The blue component, in the [0-255] range.
    */
    private Integer b;

    /**
    * The alpha component, in the [0-1] range (default: 1).
    */
    private Double a;



}
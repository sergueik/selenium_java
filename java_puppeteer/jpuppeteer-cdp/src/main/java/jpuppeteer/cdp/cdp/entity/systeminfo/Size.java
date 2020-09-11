package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* Describes the width and height dimensions of an entity.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Size {

    /**
    * Width in pixels.
    */
    private Integer width;

    /**
    * Height in pixels.
    */
    private Integer height;



}
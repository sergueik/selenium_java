package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Source offset and types for a parameter or return value.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TypeProfileEntry {

    /**
    * Source offset of the parameter or end of function for return values.
    */
    private Integer offset;

    /**
    * The types for this parameter or return value.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.TypeObject> types;



}
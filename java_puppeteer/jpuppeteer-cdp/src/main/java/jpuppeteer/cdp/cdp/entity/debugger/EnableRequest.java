package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EnableRequest {

    /**
    * The maximum size in bytes of collected scripts (not referenced by other heap objects) the debugger can hold. Puts no limit if paramter is omitted.
    */
    private Double maxScriptsCacheSize;



}
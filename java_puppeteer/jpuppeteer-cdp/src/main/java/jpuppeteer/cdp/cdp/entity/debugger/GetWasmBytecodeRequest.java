package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetWasmBytecodeRequest {

    /**
    * Id of the Wasm script to get source for.
    */
    private String scriptId;



}
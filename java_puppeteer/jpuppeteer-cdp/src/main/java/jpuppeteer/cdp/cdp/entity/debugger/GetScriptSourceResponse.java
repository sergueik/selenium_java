package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetScriptSourceResponse {

    /**
    * Script source (empty in case of Wasm bytecode).
    */
    private String scriptSource;

    /**
    * Wasm bytecode.
    */
    private String bytecode;



}
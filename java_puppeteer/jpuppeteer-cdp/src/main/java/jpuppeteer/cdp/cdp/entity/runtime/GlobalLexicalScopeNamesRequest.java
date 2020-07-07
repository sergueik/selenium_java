package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GlobalLexicalScopeNamesRequest {

    /**
    * Specifies in which execution context to lookup global scope variables.
    */
    private Integer executionContextId;



}
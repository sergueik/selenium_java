package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetVariableValueRequest {

    /**
    * 0-based number of scope as was listed in scope chain. Only 'local', 'closure' and 'catch' scope types are allowed. Other scopes could be manipulated manually.
    */
    private Integer scopeNumber;

    /**
    * Variable name.
    */
    private String variableName;

    /**
    * New variable value.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.CallArgument newValue;

    /**
    * Id of callframe that holds variable.
    */
    private String callFrameId;



}
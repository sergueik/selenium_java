package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetReturnValueRequest {

    /**
    * New return value.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.CallArgument newValue;



}
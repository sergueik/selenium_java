package jpuppeteer.cdp.cdp.entity.debugger;

/**
* Scope description.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Scope {

    /**
    * Scope type.
    */
    private String type;

    /**
    * Object representing the scope. For `global` and `with` scopes it represents the actual object; for the rest of the scopes, it is artificial transient object enumerating scope variables as its properties.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.RemoteObject object;

    /**
    */
    private String name;

    /**
    * Location in the source code where scope starts
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location startLocation;

    /**
    * Location in the source code where scope ends
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location endLocation;



}
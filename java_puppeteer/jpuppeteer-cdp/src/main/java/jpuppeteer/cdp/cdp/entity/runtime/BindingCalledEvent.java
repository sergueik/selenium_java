package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Notification is issued every time when binding is called.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BindingCalledEvent {

    /**
    */
    private String name;

    /**
    */
    private String payload;

    /**
    * Identifier of the context where the call was made.
    */
    private Integer executionContextId;



}
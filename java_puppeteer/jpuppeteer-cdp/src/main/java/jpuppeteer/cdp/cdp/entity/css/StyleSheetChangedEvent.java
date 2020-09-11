package jpuppeteer.cdp.cdp.entity.css;

/**
* Fired whenever a stylesheet is changed as a result of the client operation.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StyleSheetChangedEvent {

    /**
    */
    private String styleSheetId;



}
package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetInspectModeRequest {

    /**
    * Set an inspection mode.
    */
    private String mode;

    /**
    * A descriptor for the highlight appearance of hovered-over nodes. May be omitted if `enabled == false`.
    */
    private jpuppeteer.cdp.cdp.entity.overlay.HighlightConfig highlightConfig;



}
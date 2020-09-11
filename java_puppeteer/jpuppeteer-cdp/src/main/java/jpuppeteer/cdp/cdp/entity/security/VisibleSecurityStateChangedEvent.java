package jpuppeteer.cdp.cdp.entity.security;

/**
* The security state of the page changed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class VisibleSecurityStateChangedEvent {

    /**
    * Security state information about the page.
    */
    private jpuppeteer.cdp.cdp.entity.security.VisibleSecurityState visibleSecurityState;



}
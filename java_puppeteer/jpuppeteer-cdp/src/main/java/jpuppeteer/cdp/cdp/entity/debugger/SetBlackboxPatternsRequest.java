package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBlackboxPatternsRequest {

    /**
    * Array of regexps that will be used to check script url for blackbox state.
    */
    private java.util.List<String> patterns;



}
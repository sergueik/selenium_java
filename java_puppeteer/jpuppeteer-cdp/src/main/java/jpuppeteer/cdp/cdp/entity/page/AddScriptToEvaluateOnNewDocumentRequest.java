package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AddScriptToEvaluateOnNewDocumentRequest {

    /**
    */
    private String source;

    /**
    * If specified, creates an isolated world with the given name and evaluates given script in it. This world name will be used as the ExecutionContextDescription::name when the corresponding event is emitted.
    */
    private String worldName;



}
package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetScriptSourceRequest {

    /**
    * Id of the script to edit.
    */
    private String scriptId;

    /**
    * New content of the script.
    */
    private String scriptSource;

    /**
    * If true the change will not actually be applied. Dry run may be used to get result description without actually modifying the code.
    */
    private Boolean dryRun;



}
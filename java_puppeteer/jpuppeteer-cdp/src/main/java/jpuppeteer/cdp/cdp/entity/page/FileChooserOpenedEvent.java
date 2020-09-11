package jpuppeteer.cdp.cdp.entity.page;

/**
* Emitted only when `page.interceptFileChooser` is enabled.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FileChooserOpenedEvent {

    /**
    * Id of the frame containing input node.
    */
    private String frameId;

    /**
    * Input node id.
    */
    private Integer backendNodeId;

    /**
    * Input mode.
    */
    private String mode;



}
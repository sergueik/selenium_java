package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CreateIsolatedWorldRequest {

    /**
    * Id of the frame in which the isolated world should be created.
    */
    private String frameId;

    /**
    * An optional name which is reported in the Execution Context.
    */
    private String worldName;

    /**
    * Whether or not universal access should be granted to the isolated world. This is a powerful option, use with caution.
    */
    private Boolean grantUniveralAccess;



}
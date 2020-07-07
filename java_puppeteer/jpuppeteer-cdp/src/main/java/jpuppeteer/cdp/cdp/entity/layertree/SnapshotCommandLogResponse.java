package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SnapshotCommandLogResponse {

    /**
    * The array of canvas function calls.
    */
    private java.util.List<java.util.Map<String, Object>> commandLog;



}
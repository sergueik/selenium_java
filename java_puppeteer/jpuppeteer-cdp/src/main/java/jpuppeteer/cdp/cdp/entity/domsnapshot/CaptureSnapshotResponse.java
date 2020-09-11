package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CaptureSnapshotResponse {

    /**
    * The nodes in the DOM tree. The DOMNode at index 0 corresponds to the root document.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.domsnapshot.DocumentSnapshot> documents;

    /**
    * Shared string table that all string properties refer to with indexes.
    */
    private java.util.List<String> strings;



}
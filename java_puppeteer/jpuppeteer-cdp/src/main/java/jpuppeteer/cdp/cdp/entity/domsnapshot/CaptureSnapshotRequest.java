package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CaptureSnapshotRequest {

    /**
    * Whitelist of computed styles to return.
    */
    private java.util.List<String> computedStyles;

    /**
    * Whether to include layout object paint orders into the snapshot.
    */
    private Boolean includePaintOrder;

    /**
    * Whether to include DOM rectangles (offsetRects, clientRects, scrollRects) into the snapshot
    */
    private Boolean includeDOMRects;



}
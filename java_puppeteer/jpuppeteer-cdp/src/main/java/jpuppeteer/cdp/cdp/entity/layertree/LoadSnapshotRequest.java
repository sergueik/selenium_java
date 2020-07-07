package jpuppeteer.cdp.cdp.entity.layertree;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LoadSnapshotRequest {

    /**
    * An array of tiles composing the snapshot.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.layertree.PictureTile> tiles;



}
package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetEmulatedMediaRequest {

    /**
    * Media type to emulate. Empty string disables the override.
    */
    private String media;

    /**
    * Media features to emulate.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.emulation.MediaFeature> features;



}
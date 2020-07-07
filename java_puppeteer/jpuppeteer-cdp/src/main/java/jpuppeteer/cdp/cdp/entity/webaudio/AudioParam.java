package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Protocol object for AudioParam
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AudioParam {

    /**
    */
    private String paramId;

    /**
    */
    private String nodeId;

    /**
    */
    private String contextId;

    /**
    */
    private String paramType;

    /**
    */
    private String rate;

    /**
    */
    private Double defaultValue;

    /**
    */
    private Double minValue;

    /**
    */
    private Double maxValue;



}
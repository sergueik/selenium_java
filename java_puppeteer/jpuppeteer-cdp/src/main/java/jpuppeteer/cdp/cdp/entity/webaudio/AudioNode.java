package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Protocol object for AudioNode
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AudioNode {

    /**
    */
    private String nodeId;

    /**
    */
    private String contextId;

    /**
    */
    private String nodeType;

    /**
    */
    private Double numberOfInputs;

    /**
    */
    private Double numberOfOutputs;

    /**
    */
    private Double channelCount;

    /**
    */
    private String channelCountMode;

    /**
    */
    private String channelInterpretation;



}
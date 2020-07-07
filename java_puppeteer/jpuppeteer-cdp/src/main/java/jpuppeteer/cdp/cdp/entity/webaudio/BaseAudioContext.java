package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Protocol object for BaseAudioContext
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BaseAudioContext {

    /**
    */
    private String contextId;

    /**
    */
    private String contextType;

    /**
    */
    private String contextState;

    /**
    */
    private jpuppeteer.cdp.cdp.entity.webaudio.ContextRealtimeData realtimeData;

    /**
    * Platform-dependent callback buffer size.
    */
    private Double callbackBufferSize;

    /**
    * Number of output channels supported by audio hardware in use.
    */
    private Double maxOutputChannelCount;

    /**
    * Context sample rate.
    */
    private Double sampleRate;



}
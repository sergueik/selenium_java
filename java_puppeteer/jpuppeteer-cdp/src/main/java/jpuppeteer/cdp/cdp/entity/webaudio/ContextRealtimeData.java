package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Fields in AudioContext that change in real-time.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContextRealtimeData {

    /**
    * The current context time in second in BaseAudioContext.
    */
    private Double currentTime;

    /**
    * The time spent on rendering graph divided by render qunatum duration, and multiplied by 100. 100 means the audio renderer reached the full capacity and glitch may occur.
    */
    private Double renderCapacity;

    /**
    * A running mean of callback interval.
    */
    private Double callbackIntervalMean;

    /**
    * A running variance of callback interval.
    */
    private Double callbackIntervalVariance;



}
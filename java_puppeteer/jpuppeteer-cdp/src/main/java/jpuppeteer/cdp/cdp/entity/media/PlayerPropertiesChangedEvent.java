package jpuppeteer.cdp.cdp.entity.media;

/**
* This can be called multiple times, and can be used to set / override / remove player properties. A null propValue indicates removal.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PlayerPropertiesChangedEvent {

    /**
    */
    private String playerId;

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.media.PlayerProperty> properties;



}
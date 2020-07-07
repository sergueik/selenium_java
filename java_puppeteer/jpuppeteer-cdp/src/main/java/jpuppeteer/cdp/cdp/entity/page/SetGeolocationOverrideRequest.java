package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetGeolocationOverrideRequest {

    /**
    * Mock latitude
    */
    private Double latitude;

    /**
    * Mock longitude
    */
    private Double longitude;

    /**
    * Mock accuracy
    */
    private Double accuracy;



}
package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetTimezoneOverrideRequest {

    /**
    * The timezone identifier. If empty, disables the override and restores default host system timezone.
    */
    private String timezoneId;



}
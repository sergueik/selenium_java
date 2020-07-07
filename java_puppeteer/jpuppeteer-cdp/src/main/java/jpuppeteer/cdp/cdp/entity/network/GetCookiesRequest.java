package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetCookiesRequest {

    /**
    * The list of URLs for which applicable cookies will be fetched
    */
    private java.util.List<String> urls;



}
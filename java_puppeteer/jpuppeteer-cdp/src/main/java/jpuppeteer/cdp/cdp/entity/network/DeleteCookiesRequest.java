package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DeleteCookiesRequest {

    /**
    * Name of the cookies to remove.
    */
    private String name;

    /**
    * If specified, deletes all the cookies with the given name where domain and path match provided URL.
    */
    private String url;

    /**
    * If specified, deletes only cookies with the exact domain.
    */
    private String domain;

    /**
    * If specified, deletes only cookies with the exact path.
    */
    private String path;



}
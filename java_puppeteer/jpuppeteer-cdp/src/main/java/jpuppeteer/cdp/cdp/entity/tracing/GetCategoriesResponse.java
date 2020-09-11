package jpuppeteer.cdp.cdp.entity.tracing;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetCategoriesResponse {

    /**
    * A list of supported tracing categories.
    */
    private java.util.List<String> categories;



}
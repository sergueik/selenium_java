package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetNavigationHistoryResponse {

    /**
    * Index of the current navigation history entry.
    */
    private Integer currentIndex;

    /**
    * Array of navigation history entries.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.page.NavigationEntry> entries;



}
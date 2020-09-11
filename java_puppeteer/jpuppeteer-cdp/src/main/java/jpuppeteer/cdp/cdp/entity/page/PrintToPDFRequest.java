package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PrintToPDFRequest {

    /**
    * Paper orientation. Defaults to false.
    */
    private Boolean landscape;

    /**
    * Display header and footer. Defaults to false.
    */
    private Boolean displayHeaderFooter;

    /**
    * Print background graphics. Defaults to false.
    */
    private Boolean printBackground;

    /**
    * Scale of the webpage rendering. Defaults to 1.
    */
    private Double scale;

    /**
    * Paper width in inches. Defaults to 8.5 inches.
    */
    private Double paperWidth;

    /**
    * Paper height in inches. Defaults to 11 inches.
    */
    private Double paperHeight;

    /**
    * Top margin in inches. Defaults to 1cm (~0.4 inches).
    */
    private Double marginTop;

    /**
    * Bottom margin in inches. Defaults to 1cm (~0.4 inches).
    */
    private Double marginBottom;

    /**
    * Left margin in inches. Defaults to 1cm (~0.4 inches).
    */
    private Double marginLeft;

    /**
    * Right margin in inches. Defaults to 1cm (~0.4 inches).
    */
    private Double marginRight;

    /**
    * Paper ranges to print, e.g., '1-5, 8, 11-13'. Defaults to the empty string, which means print all pages.
    */
    private String pageRanges;

    /**
    * Whether to silently ignore invalid but successfully parsed page ranges, such as '3-2'. Defaults to false.
    */
    private Boolean ignoreInvalidPageRanges;

    /**
    * HTML template for the print header. Should be valid HTML markup with following classes used to inject printing values into them: - `date`: formatted print date - `title`: document title - `url`: document location - `pageNumber`: current page number - `totalPages`: total pages in the document  For example, `<span class=title></span>` would generate span containing the title.
    */
    private String headerTemplate;

    /**
    * HTML template for the print footer. Should use the same format as the `headerTemplate`.
    */
    private String footerTemplate;

    /**
    * Whether or not to prefer page size as defined by css. Defaults to false, in which case the content will be scaled to fit the paper size.
    */
    private Boolean preferCSSPageSize;

    /**
    * return as stream
    */
    private String transferMode;



}
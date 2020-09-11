package jpuppeteer.cdp.cdp.entity.page;

/**
* Generic font families collection.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FontFamilies {

    /**
    * The standard font-family.
    */
    private String standard;

    /**
    * The fixed font-family.
    */
    private String fixed;

    /**
    * The serif font-family.
    */
    private String serif;

    /**
    * The sansSerif font-family.
    */
    private String sansSerif;

    /**
    * The cursive font-family.
    */
    private String cursive;

    /**
    * The fantasy font-family.
    */
    private String fantasy;

    /**
    * The pictograph font-family.
    */
    private String pictograph;



}
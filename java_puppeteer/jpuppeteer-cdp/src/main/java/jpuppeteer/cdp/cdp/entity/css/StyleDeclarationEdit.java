package jpuppeteer.cdp.cdp.entity.css;

/**
* A descriptor of operation to mutate style declaration text.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StyleDeclarationEdit {

    /**
    * The css style sheet identifier.
    */
    private String styleSheetId;

    /**
    * The range of the style text in the enclosing stylesheet.
    */
    private jpuppeteer.cdp.cdp.entity.css.SourceRange range;

    /**
    * New style text.
    */
    private String text;



}
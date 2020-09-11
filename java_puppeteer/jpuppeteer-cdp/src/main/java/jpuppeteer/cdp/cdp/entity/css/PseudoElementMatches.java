package jpuppeteer.cdp.cdp.entity.css;

/**
* CSS rule collection for a single pseudo style.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PseudoElementMatches {

    /**
    * Pseudo element type.
    */
    private String pseudoType;

    /**
    * Matches of CSS rules applicable to the pseudo style.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.css.RuleMatch> matches;



}
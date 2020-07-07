package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetAttributesAsTextRequest {

    /**
    * Id of the element to set attributes for.
    */
    private Integer nodeId;

    /**
    * Text with a number of attributes. Will parse this text using HTML parser.
    */
    private String text;

    /**
    * Attribute name to replace with new attributes derived from text in case text parsed successfully.
    */
    private String name;



}
package jpuppeteer.cdp.cdp.entity.memory;

/**
* Executable module information
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Module {

    /**
    * Name of the module.
    */
    private String name;

    /**
    * UUID of the module.
    */
    private String uuid;

    /**
    * Base address where the module is loaded into memory. Encoded as a decimal or hexadecimal (0x prefixed) string.
    */
    private String baseAddress;

    /**
    * Size of the module in bytes.
    */
    private Double size;



}
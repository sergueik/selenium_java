package jpuppeteer.cdp.cdp.entity.database;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExecuteSQLResponse {

    /**
    */
    private java.util.List<String> columnNames;

    /**
    */
    private java.util.List<Object> values;

    /**
    */
    private jpuppeteer.cdp.cdp.entity.database.Error sqlError;



}
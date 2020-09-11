package jpuppeteer.cdp.cdp.entity.database;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ExecuteSQLRequest {

    /**
    */
    private String databaseId;

    /**
    */
    private String query;



}
package jpuppeteer.cdp.cdp.entity.database;

/**
* Database object.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Database {

    /**
    * Database ID.
    */
    private String id;

    /**
    * Database domain.
    */
    private String domain;

    /**
    * Database name.
    */
    private String name;

    /**
    * Database version.
    */
    private String version;



}
package jpuppeteer.cdp.cdp.entity.network;

/**
* Details of a signed certificate timestamp (SCT).
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SignedCertificateTimestamp {

    /**
    * Validation status.
    */
    private String status;

    /**
    * Origin.
    */
    private String origin;

    /**
    * Log name / description.
    */
    private String logDescription;

    /**
    * Log ID.
    */
    private String logId;

    /**
    * Issuance date.
    */
    private Double timestamp;

    /**
    * Hash algorithm.
    */
    private String hashAlgorithm;

    /**
    * Signature algorithm.
    */
    private String signatureAlgorithm;

    /**
    * Signature data.
    */
    private String signatureData;



}
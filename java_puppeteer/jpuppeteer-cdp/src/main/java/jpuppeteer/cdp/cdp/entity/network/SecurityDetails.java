package jpuppeteer.cdp.cdp.entity.network;

/**
* Security details about a request.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SecurityDetails {

    /**
    * Protocol name (e.g. "TLS 1.2" or "QUIC").
    */
    private String protocol;

    /**
    * Key Exchange used by the connection, or the empty string if not applicable.
    */
    private String keyExchange;

    /**
    * (EC)DH group used by the connection, if applicable.
    */
    private String keyExchangeGroup;

    /**
    * Cipher name.
    */
    private String cipher;

    /**
    * TLS MAC. Note that AEAD ciphers do not have separate MACs.
    */
    private String mac;

    /**
    * Certificate ID value.
    */
    private Integer certificateId;

    /**
    * Certificate subject name.
    */
    private String subjectName;

    /**
    * Subject Alternative Name (SAN) DNS names and IP addresses.
    */
    private java.util.List<String> sanList;

    /**
    * Name of the issuing CA.
    */
    private String issuer;

    /**
    * Certificate valid from date.
    */
    private Double validFrom;

    /**
    * Certificate valid to (expiration) date
    */
    private Double validTo;

    /**
    * List of signed certificate timestamps (SCTs).
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.SignedCertificateTimestamp> signedCertificateTimestampList;

    /**
    * Whether the request complied with Certificate Transparency policy
    */
    private String certificateTransparencyCompliance;



}
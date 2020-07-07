package jpuppeteer.cdp.cdp.entity.security;

/**
* Details about the security state of the page certificate.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CertificateSecurityState {

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
    * Page certificate.
    */
    private java.util.List<String> certificate;

    /**
    * Certificate subject name.
    */
    private String subjectName;

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
    * The highest priority network error code, if the certificate has an error.
    */
    private String certificateNetworkError;

    /**
    * True if the certificate uses a weak signature aglorithm.
    */
    private Boolean certificateHasWeakSignature;

    /**
    * True if the certificate has a SHA1 signature in the chain.
    */
    private Boolean certificateHasSha1Signature;

    /**
    * True if modern SSL
    */
    private Boolean modernSSL;

    /**
    * True if the connection is using an obsolete SSL protocol.
    */
    private Boolean obsoleteSslProtocol;

    /**
    * True if the connection is using an obsolete SSL key exchange.
    */
    private Boolean obsoleteSslKeyExchange;

    /**
    * True if the connection is using an obsolete SSL cipher.
    */
    private Boolean obsoleteSslCipher;

    /**
    * True if the connection is using an obsolete SSL signature.
    */
    private Boolean obsoleteSslSignature;



}
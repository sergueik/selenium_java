package jpuppeteer.cdp;

public class CDPException extends Exception {

    private int code;

    public CDPException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

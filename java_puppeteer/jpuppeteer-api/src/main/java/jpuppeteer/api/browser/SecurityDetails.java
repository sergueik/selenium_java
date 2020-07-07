package jpuppeteer.api.browser;

import java.util.Date;

public interface SecurityDetails {

    String issuer();

    String protocol();

    String subjectName();

    Date vaildFrom();

    Date vaildTo();

}

package jpuppeteer.chrome.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class SecurityDetails implements jpuppeteer.api.browser.SecurityDetails {

    private String issuer;

    private String protocol;

    private String subjectName;

    private Date vaildFrom;

    private Date vaildTo;

    @Override
    public String issuer() {
        return issuer;
    }

    @Override
    public String protocol() {
        return protocol;
    }

    @Override
    public String subjectName() {
        return subjectName;
    }

    @Override
    public Date vaildFrom() {
        return vaildFrom;
    }

    @Override
    public Date vaildTo() {
        return vaildTo;
    }
}

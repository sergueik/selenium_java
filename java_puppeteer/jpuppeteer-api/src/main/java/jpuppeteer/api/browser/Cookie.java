package jpuppeteer.api.browser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@Builder
public class Cookie {

    private String name;

    private String value;

    private String url;

    private String domain;

    private String path;

    private boolean secure;

    private boolean httpOnly;

    private String sameSite;

    private Date expires;

}

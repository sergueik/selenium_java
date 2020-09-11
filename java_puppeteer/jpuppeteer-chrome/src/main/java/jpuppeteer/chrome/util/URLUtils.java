package jpuppeteer.chrome.util;

import com.sun.webkit.network.data.Handler;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class URLUtils {

    private static final Handler DATA_PROTOCOL_HANDLER = new Handler();

    public static final URL parse(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        try {
            return new URL(null, url, DATA_PROTOCOL_HANDLER);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}

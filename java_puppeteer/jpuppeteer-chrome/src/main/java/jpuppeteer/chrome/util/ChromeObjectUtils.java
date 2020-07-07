package jpuppeteer.chrome.util;

import jpuppeteer.cdp.cdp.domain.Runtime;
import jpuppeteer.cdp.cdp.entity.runtime.ReleaseObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jpuppeteer.chrome.ChromeBrowser.DEFAULT_TIMEOUT;

public class ChromeObjectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ChromeObjectUtils.class);

    public static void releaseObjectQuietly(Runtime runtime, String objectId) {
        ReleaseObjectRequest request = new ReleaseObjectRequest();
        request.setObjectId(objectId);
        try {
            runtime.releaseObject(request, DEFAULT_TIMEOUT);
        } catch (Exception e) {
            logger.warn("release object failed, objectId={}", objectId, e);
        }
    }

}

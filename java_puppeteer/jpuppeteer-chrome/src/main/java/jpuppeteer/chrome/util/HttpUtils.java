package jpuppeteer.chrome.util;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import jpuppeteer.api.browser.Header;
import org.apache.commons.collections4.MapUtils;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {

    private static final Pattern PATTERN_CHARSET = Pattern.compile("charset=(.+)$", Pattern.CASE_INSENSITIVE);

    public static List<Header> parseHeader(Map<String, Object> headerMap) {
        List<Header> headers = Lists.newArrayListWithCapacity(0);
        if (MapUtils.isNotEmpty(headerMap)) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                String[] items = entry.getValue().toString().split("\n");
                Header header = null;
                for (String item : items) {
                    if (header == null) {
                        header = new Header(entry.getKey(), item);
                    } else {
                        header.add(item);
                    }
                }
                if (header == null) {
                    continue;
                }
                headers.add(header);
            }
        }
        return headers;
    }

    public static byte[] parseContent(String text, Boolean base64, List<Header> headers) {
        byte[] content;
        if (Boolean.TRUE.equals(base64)) {
            content = Base64.getDecoder().decode(text);
        } else {
            Charset contentEncoding = Charsets.UTF_8;
            for (Header header : headers) {
                if ("content-type".equalsIgnoreCase(header.getName())) {
                    Matcher matcher = PATTERN_CHARSET.matcher(header.getValue());
                    if (matcher.find(1)) {
                        contentEncoding = Charset.forName(matcher.group(1));
                    }
                }
            }
            content = text.getBytes(contentEncoding);
        }
        return content;
    }

}

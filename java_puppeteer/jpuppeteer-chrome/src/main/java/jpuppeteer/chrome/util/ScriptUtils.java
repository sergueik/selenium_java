package jpuppeteer.chrome.util;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ScriptUtils {

    public static String load(String path) {
        InputStream is;
        //找jar包路径
        is = ScriptUtils.class.getResourceAsStream("script/" + path);
        if (is == null) {
            //找工程路径
            is = StringUtils.class.getResourceAsStream("/script/" + path);
        }
        if (is == null) {
            return null;
        }
        try {
            InputStreamReader reader = new InputStreamReader(is);
            StringBuilder sb = new StringBuilder();
            while (true) {
                char[] chars = new char[8192];
                int size = reader.read(chars);
                if (size == -1) {
                    break;
                }
                sb.append(chars, 0, size);
            }
            return sb.toString();
        } catch (Throwable cause) {
            return null;
        }
    }

}

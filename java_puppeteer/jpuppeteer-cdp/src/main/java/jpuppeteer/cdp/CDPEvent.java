package jpuppeteer.cdp;

import com.alibaba.fastjson.JSONObject;
import jpuppeteer.cdp.cdp.CDPEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CDPEvent {

    private String sessionId;

    private CDPEventType method;

    private JSONObject params;

    public <T> T getObject(Class<T> clazz) {
        return params.toJavaObject(clazz);
    }

}

package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class SystemInfo {

    private jpuppeteer.cdp.CDPSession session;

    public SystemInfo(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Returns information about the system.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.systeminfo.GetInfoResponse getInfo(int timeout) throws Exception {
        return session.send("SystemInfo.getInfo", null, jpuppeteer.cdp.cdp.entity.systeminfo.GetInfoResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.systeminfo.GetInfoResponse> asyncGetInfo() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("SystemInfo.getInfo");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.systeminfo.GetInfoResponse>(future, jpuppeteer.cdp.cdp.entity.systeminfo.GetInfoResponse.class);
    }

    /**
    * Returns information about all running processes.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.systeminfo.GetProcessInfoResponse getProcessInfo(int timeout) throws Exception {
        return session.send("SystemInfo.getProcessInfo", null, jpuppeteer.cdp.cdp.entity.systeminfo.GetProcessInfoResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.systeminfo.GetProcessInfoResponse> asyncGetProcessInfo() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("SystemInfo.getProcessInfo");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.systeminfo.GetProcessInfoResponse>(future, jpuppeteer.cdp.cdp.entity.systeminfo.GetProcessInfoResponse.class);
    }
}
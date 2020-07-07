package jpuppeteer.cdp.cdp.constant.performance;

/**
*/
public enum SetTimeDomainRequestTimeDomain {

    TIMETICKS("timeTicks"),
    THREADTICKS("threadTicks"),
    ;

    private String value;

    SetTimeDomainRequestTimeDomain(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
}

    public static SetTimeDomainRequestTimeDomain findByValue(String value) {
        for(SetTimeDomainRequestTimeDomain val : values()) {
            if (val.value.equals(value)) return val;
        }
        return null;
    }
}
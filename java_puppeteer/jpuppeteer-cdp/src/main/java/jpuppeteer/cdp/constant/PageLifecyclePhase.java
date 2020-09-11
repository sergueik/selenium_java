package jpuppeteer.cdp.constant;

public enum PageLifecyclePhase {

    INIT("init"),
    FIRSTPAINT("firstPaint"),
    FIRSTCONTENTFULPAINT("firstContentfulPaint"),
    FIRSTMEANINGFULPAINTCANDIDATE("firstMeaningfulPaintCandidate"),
    FIRSTIMAGEPAINT("firstImagePaint"),
    DOMCONTENTLOADED("DOMContentLoaded"),
    LOAD("load"),
    NETWORKALMOSTIDLE("networkAlmostIdle"),
    FIRSTMEANINGFULPAINT("firstMeaningfulPaint"),
    NETWORKIDLE("networkIdle"),
    COMMIT("commit"),
    ;

    private String value;

    PageLifecyclePhase(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PageLifecyclePhase findByValue(String value) {
        for(PageLifecyclePhase type : PageLifecyclePhase.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}

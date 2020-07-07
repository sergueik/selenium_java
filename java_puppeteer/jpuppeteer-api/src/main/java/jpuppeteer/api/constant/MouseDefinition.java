package jpuppeteer.api.constant;

public enum MouseDefinition {

    NONE("none", 0),
    LEFT("left", 1),
    RIGHT("right", 2),
    MIDDLE("middle", 4),
    BACK("back", 8),
    FORWARD("forward", 16),

    ;

    private String name;

    private Integer code;

    MouseDefinition(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}

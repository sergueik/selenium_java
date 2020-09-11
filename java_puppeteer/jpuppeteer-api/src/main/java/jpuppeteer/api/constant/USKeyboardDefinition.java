package jpuppeteer.api.constant;

import java.util.HashMap;
import java.util.Map;

import static jpuppeteer.api.constant.USKeyboardDefinition.Constants.*;

public enum USKeyboardDefinition {

    //主键区数字键
    DIGIT0("0", null, 48, null, "Digit0", null),
    DIGIT1("1", null, 49, null, "Digit1", null),
    DIGIT2("2", null, 50, null, "Digit2", null),
    DIGIT3("3", null, 51, null, "Digit3", null),
    DIGIT4("4", null, 52, null, "Digit4", null),
    DIGIT5("5", null, 53, null, "Digit5", null),
    DIGIT6("6", null, 54, null, "Digit6", null),
    DIGIT7("7", null, 55, null, "Digit7", null),
    DIGIT8("8", null, 56, null, "Digit8", null),
    DIGIT9("9", null, 57, null, "Digit9", null),

    //数字键区数字键(Num lock)
    NUMPAD_DIGIT0("0", ")", 48, null, "Digit0", null),
    NUMPAD_DIGIT1("1", "!", 49, null, "Digit1", null),
    NUMPAD_DIGIT2("2", "@", 50, null, "Digit2", null),
    NUMPAD_DIGIT3("3", "#", 51, null, "Digit3", null),
    NUMPAD_DIGIT4("4", "$", 52, null, "Digit4", null),
    NUMPAD_DIGIT5("5", "%", 53, null, "Digit5", null),
    NUMPAD_DIGIT6("6", "^", 54, null, "Digit6", null),
    NUMPAD_DIGIT7("7", "&", 55, null, "Digit7", null),
    NUMPAD_DIGIT8("8", "*", 56, null, "Digit8", null),
    NUMPAD_DIGIT9("9", "(", 57, null, "Digit9", null),

    //字母键
    KEYA("a", "A", 65, null, "KeyA", null),
    KEYB("b", "B", 66, null, "KeyB", null),
    KEYC("c", "C", 67, null, "KeyC", null),
    KEYD("d", "D", 68, null, "KeyD", null),
    KEYE("e", "E", 69, null, "KeyE", null),
    KEYF("f", "F", 70, null, "KeyF", null),
    KEYG("g", "G", 71, null, "KeyG", null),
    KEYH("h", "H", 72, null, "KeyH", null),
    KEYI("i", "I", 73, null, "KeyI", null),
    KEYJ("j", "J", 74, null, "KeyJ", null),
    KEYK("k", "K", 75, null, "KeyK", null),
    KEYL("l", "L", 76, null, "KeyL", null),
    KEYM("m", "M", 77, null, "KeyM", null),
    KEYN("n", "N", 78, null, "KeyN", null),
    KEYO("o", "O", 79, null, "KeyO", null),
    KEYP("p", "P", 80, null, "KeyP", null),
    KEYQ("q", "Q", 81, null, "KeyQ", null),
    KEYR("r", "R", 82, null, "KeyR", null),
    KEYS("s", "S", 83, null, "KeyS", null),
    KEYT("t", "T", 84, null, "KeyT", null),
    KEYU("u", "U", 85, null, "KeyU", null),
    KEYV("v", "V", 86, null, "KeyV", null),
    KEYW("w", "W", 87, null, "KeyW", null),
    KEYX("x", "X", 88, null, "KeyX", null),
    KEYY("y", "Y", 89, null, "KeyY", null),
    KEYZ("z", "Z", 90, null, "KeyZ", null),

    //F0-N
    F1("F1", null, 112, null, "F1", null),
    F2("F2", null, 113, null, "F2", null),
    F3("F3", null, 114, null, "F3", null),
    F4("F4", null, 115, null, "F4", null),
    F5("F5", null, 116, null, "F5", null),
    F6("F6", null, 117, null, "F6", null),
    F7("F7", null, 118, null, "F7", null),
    F8("F8", null, 119, null, "F8", null),
    F9("F9", null, 120, null, "F9", null),
    F10("F10", null, 121, null, "F10", null),
    F11("F11", null, 122, null, "F11", null),
    F12("F12", null, 123, null, "F12", null),
    F13("F13", null, 124, null, "F13", null),
    F14("F14", null, 125, null, "F14", null),
    F15("F15", null, 126, null, "F15", null),
    F16("F16", null, 127, null, "F16", null),
    F17("F17", null, 128, null, "F17", null),
    F18("F18", null, 129, null, "F18", null),
    F19("F19", null, 130, null, "F19", null),
    F20("F20", null, 131, null, "F20", null),
    F21("F21", null, 132, null, "F21", null),
    F22("F22", null, 133, null, "F22", null),
    F23("F23", null, 134, null, "F23", null),
    F24("F24", null, 135, null, "F24", null),

    //功能键
    POWER("Power", null, null, null, "Power", null),
    EJECT("Eject", null, null, null, "Eject", null),
    ABORT("Cancel", null, 3, null, "Abort", null),
    HELP("Help", null, 6, null, "Help", null),
    BACKSPACE("Backspace", null, 8, null, "Backspace", null),
    TAB("Tab", null, 9, null, "Tab", null),
    ENTER("Enter", null, 13, null, "Enter", null),
    SHIFTLEFT("Shift", null, 16, null, "ShiftLeft", 1),
    SHIFTRIGHT("Shift", null, 16, null, "ShiftRight", 2),
    CONTROLLEFT("Control", null, 17, null, "ControlLeft", 1),
    CONTROLRIGHT("Control", null, 17, null, "ControlRight", 2),
    ALTLEFT("Alt", null, 18, null, "AltLeft", 1),
    ALTRIGHT("Alt", null, 18, null, "AltRight", 2),
    PAUSE("Pause", null, 19, null, "Pause", null),
    CAPSLOCK("CapsLock", null, 20, null, "CapsLock", null),
    ESCAPE("Escape", null, 27, null, "Escape", null),
    CONVERT("Convert", null, 28, null, "Convert", null),
    NONCONVERT("NonConvert", null, 29, null, "NonConvert", null),
    SPACE(" ", null, 32, null, "Space", null),
    PAGEUP("PageUp", null, 33, null, "PageUp", null),
    PAGEDOWN("PageDown", null, 34, null, "PageDown", null),
    END("End", null, 35, null, "End", null),
    HOME("Home", null, 36, null, "Home", null),
    ARROWLEFT("ArrowLeft", null, 37, null, "ArrowLeft", null),
    ARROWUP("ArrowUp", null, 38, null, "ArrowUp", null),
    ARROWRIGHT("ArrowRight", null, 39, null, "ArrowRight", null),
    ARROWDOWN("ArrowDown", null, 40, null, "ArrowDown", null),
    SELECT("Select", null, 41, null, "Select", null),
    OPEN("Execute", null, 43, null, "Open", null),
    PRINTSCREEN("PrintScreen", null, 44, null, "PrintScreen", null),
    INSERT("Insert", null, 45, null, "Insert", null),
    DELETE("Delete", null, 46, null, "Delete", null),
    NUMPADDECIMAL("\u0000", ".", 46, 110, "NumpadDecimal", 3),
    METALEFT("Meta", null, 91, null, "MetaLeft", 1),
    METARIGHT("Meta", null, 92, null, "MetaRight", 2),
    CONTEXTMENU("ContextMenu", null, 93, null, "ContextMenu", null),
    SCROLLLOCK("ScrollLock", null, 145, null, "ScrollLock", null),
    AUDIOVOLUMEMUTE("AudioVolumeMute", null, 173, null, "AudioVolumeMute", null),
    AUDIOVOLUMEDOWN("AudioVolumeDown", null, 174, null, "AudioVolumeDown", null),
    AUDIOVOLUMEUP("AudioVolumeUp", null, 175, null, "AudioVolumeUp", null),
    MEDIATRACKNEXT("MediaTrackNext", null, 176, null, "MediaTrackNext", null),
    MEDIATRACKPREVIOUS("MediaTrackPrevious", null, 177, null, "MediaTrackPrevious", null),
    MEDIASTOP("MediaStop", null, 178, null, "MediaStop", null),
    MEDIAPLAYPAUSE("MediaPlayPause", null, 179, null, "MediaPlayPause", null),
    SEMICOLON(";", ":", 186, null, "Semicolon", null),
    EQUAL("=", "+", 187, null, "Equal", null),
    NUMPADEQUAL("=", null, 187, null, "NumpadEqual", 3),
    COMMA(",", "<", 188, null, "Comma", null),
    MINUS("-", "_", 189, null, "Minus", null),
    PERIOD(".", ">", 190, null, "Period", null),
    SLASH("/", "?", 191, null, "Slash", null),
    BACKQUOTE("`", "~", 192, null, "Backquote", null),
    BRACKETLEFT("[", "{", 219, null, "BracketLeft", null),
    BACKSLASH("\\", "|", 220, null, "Backslash", null),
    BRACKETRIGHT("]", "}", 221, null, "BracketRight", null),
    QUOTE("'", "\"", 222, null, "Quote", null),
    ALTGRAPH("AltGraph", null, 225, null, "AltGraph", null),
    PROPS("CrSel", null, 247, null, "Props", null),
    CANCEL("Cancel", null, 3, null, "Abort", null),
    CLEAR("Clear", null, 12, null, "Numpad5", 3),
    SHIFT("Shift", null, 16, null, "ShiftLeft", 1),
    CONTROL("Control", null, 17, null, "ControlLeft", 1),
    ALT("Alt", null, 18, null, "AltLeft", 1),
    ACCEPT("Accept", null, 30, null, null, null),
    MODECHANGE("ModeChange", null, 31, null, null, null),
    PRINT("Print", null, 42, null, null, null),
    EXECUTE("Execute", null, 43, null, "Open", null),
    META("Meta", null, 91, null, "MetaLeft", 1),
    ATTN("Attn", null, 246, null, null, null),
    CRSEL("CrSel", null, 247, null, "Props", null),
    EXSEL("ExSel", null, 248, null, null, null),
    ERASEEOF("EraseEof", null, 249, null, null, null),
    PLAY("Play", null, 250, null, null, null),
    ZOOMOUT("ZoomOut", null, 251, null, null, null),

    //数字键区锁定
    NUMLOCK("NumLock", null, 144, null, "NumLock", null),

    //shift区
    SHIFT_DIGIT0(")", null, 48, null, "Digit0", null),
    SHIFT_DIGIT1("!", null, 49, null, "Digit1", null),
    SHIFT_DIGIT2("@", null, 50, null, "Digit2", null),
    SHIFT_DIGIT3("#", null, 51, null, "Digit3", null),
    SHIFT_DIGIT4("$", null, 52, null, "Digit4", null),
    SHIFT_DIGIT5("%", null, 53, null, "Digit5", null),
    SHIFT_DIGIT6("^", null, 54, null, "Digit6", null),
    SHIFT_DIGIT7("&", null, 55, null, "Digit7", null),
    SHIFT_DIGIT8("*", null, 56, null, "Digit8", null),
    SHIFT_DIGIT9("(", null, 57, null, "Digit9", null),
    SHIFT_KEYA("A", null, 65, null, "KeyA", null),
    SHIFT_KEYB("B", null, 66, null, "KeyB", null),
    SHIFT_KEYC("C", null, 67, null, "KeyC", null),
    SHIFT_KEYD("D", null, 68, null, "KeyD", null),
    SHIFT_KEYE("E", null, 69, null, "KeyE", null),
    SHIFT_KEYF("F", null, 70, null, "KeyF", null),
    SHIFT_KEYG("G", null, 71, null, "KeyG", null),
    SHIFT_KEYH("H", null, 72, null, "KeyH", null),
    SHIFT_KEYI("I", null, 73, null, "KeyI", null),
    SHIFT_KEYJ("J", null, 74, null, "KeyJ", null),
    SHIFT_KEYK("K", null, 75, null, "KeyK", null),
    SHIFT_KEYL("L", null, 76, null, "KeyL", null),
    SHIFT_KEYM("M", null, 77, null, "KeyM", null),
    SHIFT_KEYN("N", null, 78, null, "KeyN", null),
    SHIFT_KEYO("O", null, 79, null, "KeyO", null),
    SHIFT_KEYP("P", null, 80, null, "KeyP", null),
    SHIFT_KEYQ("Q", null, 81, null, "KeyQ", null),
    SHIFT_KEYR("R", null, 82, null, "KeyR", null),
    SHIFT_KEYS("S", null, 83, null, "KeyS", null),
    SHIFT_KEYT("T", null, 84, null, "KeyT", null),
    SHIFT_KEYU("U", null, 85, null, "KeyU", null),
    SHIFT_KEYV("V", null, 86, null, "KeyV", null),
    SHIFT_KEYW("W", null, 87, null, "KeyW", null),
    SHIFT_KEYX("X", null, 88, null, "KeyX", null),
    SHIFT_KEYY("Y", null, 89, null, "KeyY", null),
    SHIFT_KEYZ("Z", null, 90, null, "KeyZ", null),
    SHIFT_SEMICOLON(":", null, 186, null, "Semicolon", null),
    SHIFT_EQUAL("+", null, 187, null, "Equal", null),
    SHIFT_COMMA("<", null, 188, null, "Comma", null),
    SHIFT_MINUS("_", null, 189, null, "Minus", null),
    SHIFT_PERIOD(">", null, 190, null, "Period", null),
    SHIFT_SLASH("?", null, 191, null, "Slash", null),
    SHIFT_BACKQUOTE("~", null, 192, null, "Backquote", null),
    SHIFT_BRACKETLEFT("{", null, 219, null, "BracketLeft", null),
    SHIFT_BACKSLASH("|", null, 220, null, "Backslash", null),
    SHIFT_BRACKETRIGHT("}", null, 221, null, "BracketRight", null),
    SHIFT_QUOTE("\"", null, 222, null, "Quote", null),

    //数字键区功能键
    NUMPAD_PAGEUP("PageUp", "9", 33, 105, "Numpad9", 3),
    NUMPAD_PAGEDOWN("PageDown", "3", 34, 99, "Numpad3", 3),
    NUMPAD_CLEAR("Clear", "5", 12, 101, "Numpad5", 3),
    NUMPAD_END("End", "1", 35, 97, "Numpad1", 3),
    NUMPAD_HOME("Home", "7", 36, 103, "Numpad7", 3),
    NUMPAD_ARROWLEFT("ArrowLeft", "4", 37, 100, "Numpad4", 3),
    NUMPAD_ARROWUP("ArrowUp", "8", 38, 104, "Numpad8", 3),
    NUMPAD_ARROWRIGHT("ArrowRight", "6", 39, 102, "Numpad6", 3),
    NUMPAD_ARROWDOWN("ArrowDown", "2", 40, 98, "Numpad2", 3),
    NUMPAD_INSERT("Insert", "0", 45, 96, "Numpad0", 3),
    NUMPAD_ENTER("Enter", null, 13, null, "NumpadEnter", 3),
    NUMPAD_MULTIPLY("*", null, 106, null, "NumpadMultiply", 3),
    NUMPAD_ADD("+", null, 107, null, "NumpadAdd", 3),
    NUMPAD_SUBTRACT("-", null, 109, null, "NumpadSubtract", 3),
    NUMPAD_DIVIDE("/", null, 111, null, "NumpadDivide", 3),
    ;

    private String key;

    private String shiftKey;

    private Integer keyCode;

    private Integer shiftKeyCode;

    private String code;

    private Integer location;

    USKeyboardDefinition(String key, String shiftKey, Integer keyCode, Integer shiftKeyCode, String code, Integer location) {
        this.key = key;
        this.shiftKey = shiftKey;
        this.keyCode = keyCode;
        this.shiftKeyCode = shiftKeyCode;
        this.code = code;
        this.location = location;
        keyMap.put(key, this);
    }

    public String getKey() {
        return key;
    }

    public String getShiftKey() {
        return shiftKey;
    }

    public Integer getKeyCode() {
        return keyCode;
    }

    public Integer getShiftKeyCode() {
        return shiftKeyCode;
    }

    public String getCode() {
        return code;
    }

    public Integer getLocation() {
        return location;
    }

    public static USKeyboardDefinition find(String key) {
        char keyChar = key.charAt(0);
        if (keyChar == CR || keyChar == LF) {
            return ENTER;
        } else if (keyChar == Constants.SPACE) {
            return SPACE;
        } else {
            return keyMap.get(key);
        }
    }

    protected static class Constants {
        static Map<String, USKeyboardDefinition> keyMap = new HashMap<>();
        static char CR = '\r';
        static char LF = '\n';
        static char SPACE = ' ';
    }
}

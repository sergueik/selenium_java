package jpuppeteer.api.browser;


import java.util.LinkedList;
import java.util.List;

public class Header {

    private String name;

    private List<String> values;

    public Header(String name, String value) {
        this.name = name;
        this.values = new LinkedList<>();
        this.values.add(value);
    }

    public void add(String value) {
        this.values.add(value);
    }

    public void remove(String value) {
        this.values.remove(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return values.get(0);
    }

    public String[] getValues() {
        String[] tmp = new String[values.size()];
        return values.toArray(tmp);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" -> ");
        sb.append(values.toString());
        return sb.toString();
    }
}

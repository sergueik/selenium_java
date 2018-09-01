package screen.measurement;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Scale {
    PLACE_START(400, 200),
    PLACE_CHILD(200, 100);

    public double width;
    public double height;
}
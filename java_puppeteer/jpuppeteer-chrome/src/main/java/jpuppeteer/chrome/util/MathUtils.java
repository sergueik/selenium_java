package jpuppeteer.chrome.util;

public class MathUtils {

    public static double min(double a, double b, double... ds) {
        double min = Math.min(a, b);
        for(double d : ds) {
            min = Math.min(min, d);
        }
        return min;
    }

    public static double max(double a, double b, double... ds) {
        double max = Math.max(a, b);
        for(double d : ds) {
            max = Math.max(max, d);
        }
        return max;
    }

}

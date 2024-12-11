package github.zmilla93.core.language;

public class Range {

    public final int min;
    public final int max;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean contains(int value) {
        if (value < min) return false;
        return value <= max;
    }

}

package github.zmilla93.core.enums;

public enum TimeFormat {

    H12("12 Hour", "h:mm a"),
    H24("24 Hour", "HH:mm"),
    ;

    private final String name;
    private final String format;

    TimeFormat(String name, String format) {
        this.name = name;
        this.format = format;
    }

    public String toString() {
        return this.name;
    }

    public String getFormat() {
        return this.format;
    }

}

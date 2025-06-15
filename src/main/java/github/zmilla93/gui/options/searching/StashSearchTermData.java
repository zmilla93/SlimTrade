package github.zmilla93.gui.options.searching;

public class StashSearchTermData {

    public final String title;
    public final String searchTerm;
    public final int colorIndex;

    public StashSearchTermData() {
        this("", "", 0);
    }

    public StashSearchTermData(String title, String searchTerm, int colorIndex) {
        this.title = title;
        this.searchTerm = searchTerm;
        this.colorIndex = colorIndex;
    }
}

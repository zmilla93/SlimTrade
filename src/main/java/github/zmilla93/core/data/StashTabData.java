package github.zmilla93.core.data;

import github.zmilla93.core.enums.MatchType;
import github.zmilla93.gui.options.stash.StashTabType;

public class StashTabData {

    public final String stashTabName;
    public final MatchType matchType;
    public final StashTabType stashTabType;
    public final int stashColorIndex;

    public StashTabData(String name, MatchType matchType, StashTabType stashTabType, int stashColorIndex) {
        this.stashTabName = name;
        this.matchType = matchType;
        this.stashTabType = stashTabType;
        this.stashColorIndex = stashColorIndex;
    }

}

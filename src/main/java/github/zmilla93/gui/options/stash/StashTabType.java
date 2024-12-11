package github.zmilla93.gui.options.stash;

import github.zmilla93.core.utility.ZUtil;

public enum StashTabType {

    NORMAL, QUAD;

    private final String name;

    StashTabType() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}

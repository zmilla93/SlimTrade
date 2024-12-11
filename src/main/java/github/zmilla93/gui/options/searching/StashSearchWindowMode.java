package github.zmilla93.gui.options.searching;

import github.zmilla93.core.utility.ZUtil;

public enum StashSearchWindowMode {

    COMBINED, SEPARATE;

    private final String cleanName;

    StashSearchWindowMode() {
        cleanName = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return cleanName;
    }

}

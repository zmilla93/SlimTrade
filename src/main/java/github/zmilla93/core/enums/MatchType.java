package github.zmilla93.core.enums;

import github.zmilla93.core.utility.ZUtil;

public enum MatchType {

    EXACT_MATCH, CONTAINS_TEXT;

    private final String name;

    MatchType() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}

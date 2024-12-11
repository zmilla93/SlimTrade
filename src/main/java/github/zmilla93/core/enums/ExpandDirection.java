package github.zmilla93.core.enums;

import github.zmilla93.core.utility.ZUtil;

public enum ExpandDirection {

    UPWARDS, DOWNWARDS;

    private final String name;

    ExpandDirection() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }
}

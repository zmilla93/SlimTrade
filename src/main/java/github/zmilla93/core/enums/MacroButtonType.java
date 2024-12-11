package github.zmilla93.core.enums;

import github.zmilla93.core.utility.ZUtil;

public enum MacroButtonType {

    ICON, TEXT;

    private final String name;

    MacroButtonType() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }
}

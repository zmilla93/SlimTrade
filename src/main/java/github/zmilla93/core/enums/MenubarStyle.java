package github.zmilla93.core.enums;

import github.zmilla93.core.utility.ZUtil;

public enum MenubarStyle {

    DISABLED, TEXT, ICON;

    private final String name;

    MenubarStyle() {
        this.name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}

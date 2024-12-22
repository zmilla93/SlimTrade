package github.zmilla93.core.enums;

import github.zmilla93.core.utility.ZUtil;

public enum ButtonRow {

    TOP_ROW, BOTTOM_ROW;

    @Override
    public String toString() {
        return ZUtil.enumToString(name());
    }

}

package com.slimtrade.core.utility;

import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.enums.CustomIcon;

public class MacroButton {

    public enum MacroButtonType {

        ICON, TEXT;

        @Override
        public String toString() {
            return name().toLowerCase().replaceFirst(".", name().substring(0, 1));
        }
    }

    public MacroButtonType buttonType;
    public CustomIcon icon;
    public String text;
    public String lmbResponse;
    public String rmbResponse;
    public ButtonRow row;
    public boolean close;

}

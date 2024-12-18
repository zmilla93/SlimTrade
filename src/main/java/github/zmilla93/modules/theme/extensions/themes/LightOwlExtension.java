package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.modules.theme.extensions.ThemeExtension;

public class LightOwlExtension extends ThemeExtension {

    public LightOwlExtension() {
        APPROVE_KEY = "Objects.Yellow";
        APPROVE_KEY_CB = "Actions.Blue";
        DENY_KEY = "Actions.Green";
        DENY_KEY_CB = "Component.error.borderColor";
        INDETERMINATE_KEY = "Component.warning.borderColor";
        NEUTRAL_KEY = "Label.foreground";
        INCOMING_TRADE_KEY = "Objects.Yellow";
        INCOMING_TRADE_CB_KEY = "Actions.Blue";
        OUTGOING_TRADE_KEY = "Actions.Green";
        OUTGOING_TRADE_CB_KEY = "Component.error.borderColor";
        SCANNER_MESSAGE_CB_KEY = "Component.warning.borderColor";
        UPDATE_MESSAGE_CB_KEY = "Actions.Grey";
    }

}

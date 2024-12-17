package github.zmilla93.modules.theme.extensions;

import javax.swing.*;
import java.awt.*;

public class ThemeExtension {

    /// Response Colors
    protected Color approve;
    /// Approve
    public String APPROVE_KEY = "Actions.Green";
    public String APPROVE_KEY_CB = "Actions.Green";
    /// Deny
    public String DENY_KEY = "Actions.Red";
    public String DENY_KEY_CB = "Actions.Red";
    /// Indeterminate
    public String INDETERMINATE_KEY = "Actions.Yellow";
    public String INDETERMINATE_KEY_CB = "Actions.Yellow";
    /// Neutral
    public String NEUTRAL_KEY = "Label.foreground";

    public Color deny = Color.RED;
    public Color indeterminate = Color.ORANGE;

    /// Message Colors
    public Color incoming = Color.GREEN;
    public Color outgoing = Color.RED;
    public Color scanner = Color.ORANGE;
    public Color update = Color.CYAN;

    /// Alias for "Label.foreground"
    private Color textColor;

    public Color approve() {
        if (approve == null) approve = Color.PINK;
        return approve;
    }

    public Color getTextColor() {
        if (textColor == null) textColor = UIManager.getColor("Label.foreground");
        return textColor;
    }

}

package github.zmilla93.modules.theme.extensions;

import javax.swing.*;
import java.awt.*;

public class ThemeExtension {

    /// Response Colors
    protected Color approve;

    public String APPROVE_KEY = "Objects.Green";
    public String DENY_KEY = "Objects.Red";
    public String INDETERMINATE_KEY = "Objects.Yellow";
    public String NEUTRAL_KEY = "Label.foreground";

    //    public Color approve = Color.GREEN;
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

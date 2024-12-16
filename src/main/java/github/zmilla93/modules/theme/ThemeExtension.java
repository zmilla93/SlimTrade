package github.zmilla93.modules.theme;

import javax.swing.*;
import java.awt.*;

public class ThemeExtension {

    /// Response Colors
    protected Color approve;
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

    public ThemeExtension() {

    }

    public Color approve() {
        if (approve == null) approve = Color.GREEN;
        return approve;
//        if(approve == null) approve = UIManager.getColor("");
    }

    public Color getTextColor() {
        if (textColor == null) textColor = UIManager.getColor("Label.foreground");
        return textColor;
    }

}

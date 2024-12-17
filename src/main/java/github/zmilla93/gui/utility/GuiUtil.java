package github.zmilla93.gui.utility;

import javax.swing.*;

public class GuiUtil {

    private GuiUtil() {
        /// Static class
    }

    /**
     * Equivalent to using setForegroundColor(UIManager.getColor(uiKey)),
     * except color will update when the current theme changes.
     */
    public static void setLabelColorKey(JLabel label, String uiKey) {
        label.putClientProperty("UIManagerKey", uiKey);
        label.setForeground(UIManager.getColor(uiKey));
        label.addPropertyChangeListener("UI", e -> label.setForeground(UIManager.getColor(label.getClientProperty("UIManagerKey"))));
    }

}

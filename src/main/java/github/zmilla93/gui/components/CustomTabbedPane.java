package github.zmilla93.gui.components;

import javax.swing.*;
import java.awt.*;

public class CustomTabbedPane extends JTabbedPane {

    public Component[] getTabbedComponents() {
        Component[] components = new Component[getTabCount()];
        for (int i = 0; i < getTabCount(); i++) {
            components[i] = getComponentAt(i);
        }
        return components;
    }

}

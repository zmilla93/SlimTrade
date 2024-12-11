package github.zmilla93.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Wraps a single component with an inset.
 */
public class BufferPanel extends JPanel {

    public BufferPanel(JComponent component, int inset) {
        this(component, inset, inset, inset, inset);
    }

    public BufferPanel(JComponent component, int top, int left, int bottom, int right) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(top, left, bottom, right);
        add(component, gc);
    }

}

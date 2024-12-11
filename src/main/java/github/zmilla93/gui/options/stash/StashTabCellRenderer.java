package github.zmilla93.gui.options.stash;

import javax.swing.*;
import java.awt.*;

// FIXME: Make this color not object?
public class StashTabCellRenderer extends JPanel implements ListCellRenderer<Color> {

    private static final long serialVersionUID = 1L;

    public StashTabCellRenderer() {
        this.setPreferredSize(new Dimension(30, 20));
        setOpaque(true);
    }

    boolean active = false;

    public void setBackground(Color bg) {
        if (!active) {
            return;
        }
        super.setBackground(bg);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index, boolean isSelected, boolean cellHasFocus) {
        active = true;
        if (isSelected) {
            this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        } else {
            this.setBorder(BorderFactory.createEmptyBorder());
        }
        setBackground(value);
        active = false;
        return this;
    }

}
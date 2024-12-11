package github.zmilla93.gui.overlays;

import github.zmilla93.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class DummyMenubar extends JPanel {

    private static final int INSET_HORIZONTAL = 35;
    private static final int INSET_VERTICAL = 20;

    public DummyMenubar(String text) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.insets = new Insets(INSET_VERTICAL, INSET_HORIZONTAL, INSET_VERTICAL, INSET_HORIZONTAL);
        add(new JLabel(text), gc);
    }

}

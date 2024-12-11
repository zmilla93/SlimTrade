package github.zmilla93.gui.setup;

import github.zmilla93.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class FinishSetupPanel extends JPanel {

    public FinishSetupPanel() {
        GridBagConstraints gc = ZUtil.getGC();
        setLayout(new GridBagLayout());
        add(new JLabel("Setup complete!"), gc);
        gc.gridy++;
        add(new JLabel("Stay sane, exile."), gc);
    }

}

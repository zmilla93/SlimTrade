package github.zmilla93.gui.options;

import github.zmilla93.gui.options.general.BasicsPanel;
import github.zmilla93.gui.options.general.EnableFeaturesPanel;
import github.zmilla93.gui.options.general.HistoryOptionPanel;
import github.zmilla93.gui.options.general.MessageSettingsPanel;

import java.awt.*;

public class GeneralOptionPanel extends AbstractOptionPanel {

    public GeneralOptionPanel() {
//        addRow("Kalguur Helper", new KalguurOptionPanel());
        addRow("Basics", new BasicsPanel());
        addRow("Enable Features", new EnableFeaturesPanel());
        addRow("Message Popups", new MessageSettingsPanel());
        addRow("History", new HistoryOptionPanel());
    }

    private void addRow(String title, Component component) {
        addRow(title, component, true);
    }

    private void addRow(String title, Component component, boolean addStrut) {
        addHeader(title);
        addComponent(component);
        if (addStrut) addVerticalStrut();
    }

}

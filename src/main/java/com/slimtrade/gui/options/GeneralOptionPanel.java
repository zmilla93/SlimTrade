package com.slimtrade.gui.options;

import com.slimtrade.gui.options.general.*;

import java.awt.*;

public class GeneralOptionPanel extends AbstractOptionPanel {

    public GeneralOptionPanel() {
        addRow("Kalguur Helper", new KalguurOptionPanel());
        addRow("Basics", new BasicsPanel());
        addRow("Enable Features", new EnableFeaturesPanel());
        addRow("Message Popups", new MessageSettingsPanel());
        addRow("History", new HistoryOptionPanel());
        addHeader("Path of Exile");
        addComponent(new ClientPathPanel());
        addComponent(new PoeChatHotkeyPanel());
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

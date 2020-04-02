package com.slimtrade.gui.stash;

import com.slimtrade.App;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.saving.StashTab;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StashTabPanel extends ContainerPanel implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;

    public static final AddRemovePanel rowContainer = new AddRemovePanel();
    private GridBagConstraints gcRow;
    private JPanel stashOptions;

    public StashTabPanel() {
        this.setVisible(false);
        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gcRow = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gcRow.gridx = 0;
        gcRow.gridy = 0;
        gcRow.insets.bottom = 5;

        stashOptions = new JPanel();
        stashOptions.setLayout(new GridBagLayout());

        JButton addButton = new BasicButton("Add Stash Tab");

        gc.insets.bottom = 0;
        container.add(new CustomLabel("Add stash names for color coding or marking quad tabs."), gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        container.add(new CustomLabel("Default white ignores color coding if you only want to mark a quad tab."), gc);
        gc.gridy++;
        container.add(addButton, gc);
        gc.gridy++;
        container.add(rowContainer, gc);

        addButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                addNewRow();
                revalidate();
            }
        });
    }

    private StashTabRow addNewRow() {
        StashTabRow row = new StashTabRow(rowContainer);
        rowContainer.addRemoveablePanel(row);
        return row;
    }

    public void save() {
        App.saveManager.saveFile.stashTabs.clear();
        rowContainer.clearHiddenPanels();
        for (Component c : rowContainer.getComponents()) {
            StashTabRow row = (StashTabRow) c;
            App.saveManager.saveFile.stashTabs.add(row.getStashTabData());
        }
    }

    public void load() {
        rowContainer.removeAll();
        for (StashTab tab : App.saveManager.saveFile.stashTabs) {
            StashTabRow row = addNewRow();
            row.setText(tab.name);
            row.setType(tab.type);
            row.setColor(tab.color);
        }
        rowContainer.clearHiddenPanels();
    }

    @Override
    public void updateColor() {
        super.updateColor();
        stashOptions.setBackground(Color.GREEN);
    }

}

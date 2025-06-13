package github.zmilla93.gui.options.ignore;

import github.zmilla93.core.data.IgnoreItemData;
import github.zmilla93.core.enums.MatchType;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.windows.CustomDialog;

import java.awt.*;

public class ItemIgnoreWindow extends CustomDialog {

    private final IgnoreInputPanel inputPanel = new IgnoreInputPanel();

    public ItemIgnoreWindow() {
        super("Ignore Item");
        setMinimumSize(new Dimension(0, 0));
        pinButton.setVisible(false);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        final int INSET = 20;
        gc.insets = new Insets(INSET, INSET, INSET, INSET);
        contentPanel.add(inputPanel, gc);
        pack();
        setMinimumSize(getSize());
        addListeners();
    }

    private void addListeners() {
        inputPanel.getIgnoreButton().addActionListener(e -> {
            FrameManager.optionsWindow.ignorePanel.tryAddIgnoreItem(inputPanel.getIgnoreItem());
            FrameManager.optionsWindow.ignorePanel.save();
            SaveManager.ignoreSaveFile.saveToDisk();
            setVisible(false);
        });
    }

    public void showWindow(String itemName) {
        inputPanel.setIgnoreItem(new IgnoreItemData(itemName, MatchType.EXACT_MATCH, 60));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        revalidate();
        repaint();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) inputPanel.getIgnoreButton().requestFocus();
    }

}

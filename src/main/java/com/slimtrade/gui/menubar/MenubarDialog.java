package com.slimtrade.gui.menubar;

import com.slimtrade.App;
import com.slimtrade.core.chatparser.IDndListener;
import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.IThemeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class MenubarDialog extends BasicDialog implements ISaveListener, IFontChangeListener, IThemeListener, IParserLoadedListener, IDndListener {

    private JButton optionsButton;
    private JButton chatScannerButton;
    private JButton historyButton;
    private JButton hideoutButton;
    private JButton dndButton;
    private JButton exitButton;
    private final Component horizontalSeparator;
    private final Component verticalSeparator;
    private static final int EXIT_INSET = 8;
    private static final String DND_ENABLED = "DND is On";
    private static final String DND_DISABLED = "DND is Off";

    public MenubarDialog() {
        horizontalSeparator = Box.createHorizontalStrut(EXIT_INSET);
        verticalSeparator = Box.createVerticalStrut(EXIT_INSET);
        rebuild();
        ThemeManager.addFontListener(this);
        ThemeManager.addThemeListener(this);
        SaveManager.settingsSaveFile.addListener(this);
    }

    private void addListeners() {
        optionsButton.addActionListener(e -> FrameManager.optionsWindow.setVisible(true));
        historyButton.addActionListener(e -> FrameManager.historyWindow.setVisible(true));
        chatScannerButton.addActionListener(e -> FrameManager.chatScannerWindow.setVisible(true));
        // FIXME:
        hideoutButton.addActionListener(e -> POEInterface.pasteWithFocus("/hideout"));
        dndButton.addActionListener(e -> POEInterface.pasteWithFocus("/dnd"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void rebuild() {
//        if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.ICON) buildIconButtons();
//        else buildTextButtons();
        buildTextButtons();
        if (App.chatParser != null) updateDndButton(App.chatParser.isDndEnabled());
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    private void buildIconButtons() {
        contentPanel.removeAll();
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        optionsButton = new IconButton(DefaultIcon.COG);
        historyButton = new IconButton(DefaultIcon.CHART);
        chatScannerButton = new IconButton(DefaultIcon.SCANNER);
        hideoutButton = new IconButton(DefaultIcon.HOME);
        dndButton = new IconButton(DefaultIcon.TAG);
        exitButton = new IconButton(DefaultIcon.POWER);

        for (Component comp : getOrderedComponents()) contentPanel.add(comp);
        addListeners();
        pack();
    }

    private void buildTextButtons() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;

        optionsButton = new MenubarButton("Options");
        historyButton = new MenubarButton("History");
        chatScannerButton = new MenubarButton("Chat Scanner");
        hideoutButton = new MenubarButton("Hideout");
        dndButton = new MenubarButton(DND_DISABLED);
        exitButton = new MenubarButton("Exit");

        for (Component comp : getOrderedComponents()) {
            contentPanel.add(comp, gc);
            gc.gridy++;
        }
        addListeners();
        pack();
    }

    private Component[] getOrderedComponents() {
        Anchor anchor = SaveManager.overlaySaveFile.data.menubarAnchor;
        Component[] components;
        components = new Component[]{
                optionsButton, historyButton,
                chatScannerButton, hideoutButton, dndButton,
                horizontalSeparator, verticalSeparator, exitButton};
        if (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT)
            Collections.reverse(Arrays.asList(components));
//        boolean reverse = false;
//        if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.ICON && (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT))
//            reverse = true;
//        else if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.TEXT && (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT))
//            reverse = true;
//        if (reverse) Collections.reverse(Arrays.asList(components));
        return components;
    }

    private void handleResize() {
        pack();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    @Override
    public void onFontChanged() {
        handleResize();
    }

    @Override
    public void onThemeChange() {
        handleResize();
    }

    private void updateDndButton(boolean dnd) {
        SwingUtilities.invokeLater(() -> {
            if (dndButton instanceof IconButton) {
                IconButton dndIconButton = (IconButton) dndButton;
                if (dnd) dndIconButton.setIcon(DefaultIcon.VOLUME_MUTE);
                else dndIconButton.setIcon(DefaultIcon.VOLUME_DOWN);
            } else {
                if (dnd) dndButton.setText(DND_ENABLED);
                else dndButton.setText(DND_DISABLED);
            }
            handleResize();
        });
    }

    @Override
    public void onDndToggle(boolean state, boolean loaded) {
        if (!loaded) return;
        updateDndButton(state);
    }

    @Override
    public void onParserLoaded() {
        updateDndButton(App.chatParser.isDndEnabled());
    }

    @Override
    public void onSave() {
        rebuild();
    }

}

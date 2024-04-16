package com.slimtrade.gui.menubar;

import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.IThemeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class MenubarDialog extends BasicDialog implements IFontChangeListener, IThemeListener {

    private JButton optionsButton;
    private JButton chatScannerButton;
    private JButton historyButton;
    private JButton hideoutButton;
    private JButton exitButton;
    private final Component horizontalSeparator;
    private final Component verticalSeparator;
    private static final int EXIT_INSET = 8;
    private static final int INSET = 1;

    public MenubarDialog() {
        horizontalSeparator = Box.createHorizontalStrut(EXIT_INSET);
        verticalSeparator = Box.createVerticalStrut(EXIT_INSET);
        rebuild();
        ThemeManager.addFontListener(this);
        ThemeManager.addThemeListener(this);
    }

    private void addListeners() {
        optionsButton.addActionListener(e -> FrameManager.optionsWindow.setVisible(true));
        historyButton.addActionListener(e -> FrameManager.historyWindow.setVisible(true));
        chatScannerButton.addActionListener(e -> FrameManager.chatScannerWindow.setVisible(true));
        // FIXME:
        hideoutButton.addActionListener(e -> POEInterface.pasteWithFocus("/hideout"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void rebuild() {
//        buildIconButtons();
        buildTextButtons();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    private void buildIconButtons() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
        GridBagConstraints gc = ZUtil.getGC();
        optionsButton = new IconButton(DefaultIcon.LIST);
        historyButton = new IconButton(DefaultIcon.STOPWATCH);
        chatScannerButton = new IconButton(DefaultIcon.SCANNER);
        hideoutButton = new IconButton(DefaultIcon.HOME);
        exitButton = new IconButton(DefaultIcon.POWER);
        gc.insets = new Insets(INSET, INSET, INSET, INSET);

        Component[] components = getOrderedComponents(SaveManager.overlaySaveFile.data.menubarAnchor);

        for (int i = 0; i < components.length; i++) {
            Component c = components[i];
            if (i == getComponentCount() - 1) gc.insets.right = INSET;
//            contentPanel.add(c, gc);
            contentPanel.add(c);
            gc.gridx++;
            gc.insets.left = 0;
        }

        addListeners();
        pack();
    }

    private void buildTextButtons() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        optionsButton = new MenubarButton("Options");
        historyButton = new MenubarButton("History");
        chatScannerButton = new MenubarButton("Chat Scanner");
        hideoutButton = new MenubarButton("Hideout");
        exitButton = new MenubarButton("Exit");
        gc.fill = GridBagConstraints.BOTH;
        contentPanel.add(optionsButton, gc);
        gc.gridy++;
        contentPanel.add(historyButton, gc);
        gc.gridy++;
        contentPanel.add(chatScannerButton, gc);
        gc.gridy++;
        contentPanel.add(hideoutButton, gc);
        gc.gridy++;
        contentPanel.add(Box.createVerticalStrut(EXIT_INSET), gc);
        gc.gridy++;
        contentPanel.add(exitButton, gc);
        gc.gridy++;
        addListeners();
        pack();
    }

    private Component[] getOrderedComponents(Anchor anchor) {
        Component[] components;
        components = new Component[]{historyButton, chatScannerButton, hideoutButton, optionsButton, horizontalSeparator, verticalSeparator, exitButton};
        if (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT)
            components = new Component[]{exitButton, horizontalSeparator, verticalSeparator, optionsButton, hideoutButton, chatScannerButton, historyButton};
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
        pack();
    }

}

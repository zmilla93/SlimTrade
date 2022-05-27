package com.slimtrade.gui.menubar;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.colortheme.IThemeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenubarDialog extends BasicDialog implements IThemeListener {

    private JButton optionsButton;
    private JButton chatScannerButton;
    private JButton historyButton;
    private JButton hideoutButton;
    private JButton exitButton;
    private final int EXIT_INSET = 8;
    private final int INSET = 1;

    public MenubarDialog() {
        buildIconButtons();
        ColorManager.addListener(this);
    }

    private void addListeners() {
        optionsButton.addActionListener(e -> FrameManager.optionsWindow.setVisible(true));
        historyButton.addActionListener(e -> FrameManager.historyWindow.setVisible(true));
        chatScannerButton.addActionListener(e -> FrameManager.chatScannerWindow.setVisible(true));
        // FIXME:
        hideoutButton.addActionListener(e -> POEInterface.pasteWithFocus("/hideout"));
        exitButton.addActionListener(e -> System.exit(0));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hideIfOutOfBounds(e.getPoint());
            }
        });

        addButtonExitListener(optionsButton);
        addButtonExitListener(historyButton);
        addButtonExitListener(chatScannerButton);
        addButtonExitListener(historyButton);
        addButtonExitListener(exitButton);
    }

    private void addButtonExitListener(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                hideIfOutOfBounds(e.getPoint());
            }
        });
    }

    private void hideIfOutOfBounds(Point point){
        if (contains(point)) return;
        setVisible(false);
        FrameManager.menubarIcon.setVisible(true);
    }

    private void buildIconButtons() {
        container.removeAll();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        optionsButton = new IconButton("/icons/default/th-list.png");
        historyButton = new IconButton("/icons/default/stopwatch.png");
        chatScannerButton = new IconButton("/icons/default/eye.png");
        hideoutButton = new IconButton("/icons/custom/homex64.png");
        exitButton = new IconButton("/icons/default/power.png");
        gc.insets = new Insets(INSET, INSET, INSET, INSET);
        container.add(optionsButton, gc);
        gc.gridx++;
        gc.insets.left = 0;
        container.add(historyButton, gc);
        gc.gridx++;
        container.add(chatScannerButton, gc);
        gc.gridx++;
        container.add(hideoutButton, gc);
        gc.gridx++;
        container.add(Box.createHorizontalStrut(EXIT_INSET), gc);
        gc.gridx++;
        container.add(exitButton, gc);
        gc.gridx++;
        addListeners();
        pack();
    }

    private void buildTextButtons() {
        container.removeAll();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        optionsButton = new MenubarButton("Options");
        historyButton = new MenubarButton("History");
        chatScannerButton = new MenubarButton("Chat Scanner");
        exitButton = new MenubarButton("Exit");
        gc.fill = GridBagConstraints.BOTH;
        container.add(optionsButton, gc);
        gc.gridy++;
        container.add(historyButton, gc);
        gc.gridy++;
        container.add(chatScannerButton, gc);
        gc.gridy++;
        container.add(Box.createVerticalStrut(EXIT_INSET), gc);
        gc.gridy++;
        container.add(exitButton, gc);
        gc.gridy++;
        addListeners();
        pack();
    }

    @Override
    public void onThemeChange() {
//        pack();
//        container.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Label.foreground")));
    }
}

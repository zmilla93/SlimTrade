package com.slimtrade.gui.menubar;

import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.colortheme.IconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenubarButtonDialog extends BasicDialog {

    private JPanel iconPanel = new JPanel();

    public MenubarButtonDialog() {

        ImageIcon icon = IconFactory.getIcon("/icons/default/tagx64.png");
//        JLabel iconLabel = new JLabel(icon);
        JButton iconButton = new IconButton("/icons/default/tagx64.png");
        container.setLayout(new BorderLayout());
        container.add(iconButton, BorderLayout.CENTER);
        pack();
        iconButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setVisible(false);
                FrameManager.menubarDialog.setVisible(true);
            }
        });
    }

}

package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.components.InsetPanel;
import com.slimtrade.gui.stash.GridPanel;
import com.slimtrade.modules.colortheme.ColorTheme;
import com.slimtrade.modules.colortheme.IThemeListener;

import javax.swing.*;
import java.awt.*;

public class StashWindow extends CustomDialog implements IThemeListener {

//    private Container container;

    public StashWindow() {
        setTitle("Stash Overlay");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
//        this.container = getContentPane();

//        JPanel testPanel = new JPanel();
//        testPanel.setPreferredSize(new Dimension(400, 400));
//        testPanel.setBackground(Color.RED);
        GridPanel gridPanel = new GridPanel();
        InsetPanel panel = new InsetPanel(new Insets(20, 20, 20, 20)){
            @Override
            public void updateUI() {
                super.updateUI();
//                Color c = UIManager.getColor("Panel.background");
//                setBorderBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 255/8));
            }
        };
        panel.contentPanel.setLayout(new BorderLayout());
        panel.contentPanel.add(gridPanel, BorderLayout.CENTER);
        panel.contentPanel.setOpaque(false);


        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.CENTER);


        contentPanel.setBackground(ColorManager.TRANSPARENT);

        pack();
        setSize(200, 200);
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        System.out.println("WEW");
        Color c = UIManager.getColor("Panel.background");
        setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 128));
    }

    @Override
    public void onThemeChange() {
        System.out.println("hmm");
        Color c = UIManager.getColor("Panel.background");
        contentPanel.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 128));
    }
}

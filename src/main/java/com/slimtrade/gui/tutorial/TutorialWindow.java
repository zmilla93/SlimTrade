package com.slimtrade.gui.tutorial;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.tutorial.panels.MenuBarPanel;
import com.slimtrade.gui.tutorial.panels.ScannerPanel;
import com.slimtrade.gui.tutorial.panels.StashHelperPanel;
import com.slimtrade.gui.tutorial.panels.TradePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TutorialWindow extends AbstractResizableWindow {

    private JPanel buttonPanel;

    private JButton backButton;
    private JButton nextButton;

    private ArrayList<JPanel> panels = new ArrayList<>();
    private int panelIndex = 0;

    public TutorialWindow() {
        super("Tutorial", false);

        TradePanel tradePanel = new TradePanel();
        StashHelperPanel stashHelperPanel = new StashHelperPanel();
        ScannerPanel scannerPanel = new ScannerPanel();
        MenuBarPanel menuBarPanel = new MenuBarPanel();

        // Add panels to list
        panels.add(tradePanel);
        panels.add(stashHelperPanel);
        panels.add(scannerPanel);
        panels.add(menuBarPanel);


        // Button Panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 35, 5));
        backButton = new BasicButton("Back");
        nextButton = new BasicButton("Next");
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        container.setLayout(new BorderLayout());
        container.add(panels.get(0), BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        updateButtons();
        createListeners();
        this.updateColor();
        this.pack();
        setMinimumSize(getSize());
        FrameManager.centerFrame(this);
    }

    private void updateButtons() {
        if(panelIndex == 0) {
            backButton.setVisible(false);
        } else {
            backButton.setVisible(true);
        }
        if(panelIndex == panels.size()-1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
    }

    private void createListeners() {
        nextButton.addActionListener(e -> {
            if(panelIndex < panels.size()-1) {
                container.remove(panels.get(panelIndex));
                panelIndex++;
                container.add(panels.get(panelIndex), BorderLayout.CENTER);
                updateButtons();
                revalidate();
                repaint();
                setMinimumSize(null);
                setPreferredSize(null);
                pack();
                setMinimumSize(getSize());
                FrameManager.centerFrame(this);
            } else {
                FrameManager.destoryTutorialWindow();
            }
        });

        backButton.addActionListener(e -> {
            if(panelIndex > 0) {
                container.remove(panels.get(panelIndex));
                panelIndex--;
                container.add(panels.get(panelIndex), BorderLayout.CENTER);
                updateButtons();
                revalidate();
                repaint();
                setMinimumSize(null);
                setPreferredSize(null);
                pack();
                setMinimumSize(getSize());
                FrameManager.centerFrame(this);
            }
        });
    }

}

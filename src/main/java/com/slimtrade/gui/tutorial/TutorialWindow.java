package com.slimtrade.gui.tutorial;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.tutorial.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TutorialWindow extends AbstractResizableWindow implements IColorable {

    private JPanel buttonPanel;
    private JPanel countPanel;

    private JLabel countLabel = new JLabel();
    private JButton backButton;
    private JButton nextButton;

    private ArrayList<JPanel> panels = new ArrayList<>();
    private int panelIndex = 0;
    private final int BOTTOM_SPACER = 30;

    public static final Color BACKGROUND_COLOR = ColorManager.BACKGROUND;

    public TutorialWindow() {
        super("Tutorial", false);

        TradePanel tradePanel = new TradePanel();
        StashHelperPanel stashHelperPanel = new StashHelperPanel();
        ScannerPanel scannerPanel = new ScannerPanel();
        HistoryTutorialPanel historyTutorialPanel = new HistoryTutorialPanel();
        MenuBarPanel menuBarPanel = new MenuBarPanel();

        // Add panels to list
        panels.add(tradePanel);
        panels.add(stashHelperPanel);
        panels.add(scannerPanel);
        panels.add(historyTutorialPanel);
        panels.add(menuBarPanel);

        // Count Panel
        countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        countPanel.add(countLabel);

        // Button Panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, BOTTOM_SPACER, 5));
        backButton = new BasicButton("Back");
        nextButton = new BasicButton("Next");
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(countPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Container
        container.setLayout(new BorderLayout());
        container.add(panels.get(0), BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);

        updateButtons();
        createListeners();

        this.updateColor();
        this.pack();
        setMinimumSize(getSize());
        FrameManager.centerFrame(this);

        bottomPanel.setBackground(BACKGROUND_COLOR);
        countPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);

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
        countLabel.setText((panelIndex + 1) + "/" + panels.size());
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
                FrameManager.destroyTutorialWindow();
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

    @Override
    public void updateColor() {
        super.updateColor();
        for(JPanel p : panels) {
            App.eventManager.recursiveColor(p);
        }
    }

}

package com.slimtrade.gui.setup;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.managers.SetupManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.setup.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SetupWindow extends JFrame implements IColorable {

    private Container container;
    private JPanel innerPanel;
    private JPanel buttonPanel;

    private ClientPanel clientPanel = new ClientPanel(this);
    private CharacterPanel characterPanel = new CharacterPanel();
    private StashPanel stashPanel = new StashPanel();
    private CompletePanel completePanel = new CompletePanel();

    private ArrayList<JPanel> panels = new ArrayList<>();

    private JButton backButton;
    private JButton nextButton;

    private int panelIndex = 0;

    public SetupWindow() {
        this.setTitle(References.APP_NAME + " - Setup");
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("icons/default/tagx64.png")).getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        innerPanel = new JPanel(FrameManager.gridBag);

        // Panel List
        if(SetupManager.clientSetupCheck) {
            panels.add(clientPanel);
        }
        if(SetupManager.characterNameCheck) {
            panels.add(characterPanel);
        }
        if(SetupManager.stashOverlayCheck) {
            panels.add(stashPanel);
        }
        if(SetupManager.clientSetupCheck) {

        }
        panels.add(completePanel);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        // Inner Panel
        innerPanel.add(panels.get(0), gc);


        // Button Panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 35, 5));
        backButton = new BasicButton("Back");
        nextButton = new BasicButton("Next");
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        // Finish
        refreshButtons();
        createListeners();
        container.add(innerPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setMinimumSize(new Dimension(600, 300));
        App.eventManager.recursiveColor(this);
        App.eventManager.recursiveColor(clientPanel);
        App.eventManager.recursiveColor(stashPanel);
        App.eventManager.recursiveColor(characterPanel);
        App.eventManager.recursiveColor(completePanel);
        FrameManager.centerFrame(this);
    }

    public void refreshButtons() {
        if(panelIndex == 0) {
            backButton.setVisible(false);
        } else {
            backButton.setVisible(true);
        }
        if(panelIndex < panels.size()-1) {
            nextButton.setText("Next");
        } else {
            nextButton.setText("Finish");
        }
        if(panels.get(panelIndex) instanceof ISetupValidator) {
            if(((ISetupValidator) panels.get(panelIndex)).isValidInput()) {
                nextButton.setEnabled(true);
            } else {
                nextButton.setEnabled(false);
            }
        }
    }

    private void createListeners(){
        backButton.addActionListener(e -> {
            if(panelIndex == 0) {
                return;
            }
            innerPanel.remove(panels.get(panelIndex));
            panelIndex--;
            GridBagConstraints gc = new GridBagConstraints();
            gc.gridx = 0;
            gc.gridy = 0;
            innerPanel.add(panels.get(panelIndex), gc);
            refreshButtons();
            revalidate();
            repaint();
        });

        nextButton.addActionListener(e -> {
            if(panels.get(panelIndex) instanceof ISetupValidator) {
                ((ISetupValidator) panels.get(panelIndex)).save();
            }
            if(panelIndex < panels.size()-1) {
                innerPanel.remove(panels.get(panelIndex));
                panelIndex++;
                GridBagConstraints gc = new GridBagConstraints();
                gc.gridx = 0;
                gc.gridy = 0;
                innerPanel.add(panels.get(panelIndex), gc);
                refreshButtons();
                revalidate();
                repaint();
            } else if(panelIndex == panels.size()-1) {
                this.setAlwaysOnTop(false);
                App.saveManager.saveToDisk();
                this.dispose();
                App.launch();
                FrameManager.showTutorialWindow();
            }
        });


    }

    @Override
    public void updateColor() {
        innerPanel.setBackground(ColorManager.BACKGROUND);
        buttonPanel.setBackground(ColorManager.BACKGROUND);
    }
}

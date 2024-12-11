package com.slimtrade.gui.setup;

import com.slimtrade.App;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.enums.SetupPhase;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.POEWindow;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.BufferPanel;
import com.slimtrade.gui.components.CardPanel;
import com.slimtrade.gui.listening.IDefaultSizeAndLocation;
import com.slimtrade.gui.managers.SetupManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class SetupWindow extends JFrame implements IDefaultSizeAndLocation {

    // Card Panel - startPanel > setupPanels[] > finishPanel
    private final CardPanel cardPanel = new CardPanel();
    private final StartSetupPanel startPanel = new StartSetupPanel();
    private final FinishSetupPanel finishPanel = new FinishSetupPanel();
    // Card Panel Components
    private static final String NEXT_TEXT = "Next";
    private static final String FINISH_TEXT = "Finish";
    private final JButton previousButton = new JButton("Previous");
    public final JButton nextButton = new JButton(NEXT_TEXT);
    private final JLabel countLabel = new JLabel("10/10");
    // Setup Panels
    private final ArrayList<AbstractSetupPanel> setupPanels = new ArrayList<>();
    private final InstallFolderSetupPanel clientPanel = new InstallFolderSetupPanel();
    private final GameDetectionSetupPanel gameDetectionPanel = new GameDetectionSetupPanel();
    private final StashFolderSetupPanel stashFolderPanel = new StashFolderSetupPanel();

    public SetupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SlimTrade Setup");
        // FIXME : Make a window that handles icons automatically so this code can be reused?
        ArrayList<Image> images = new ArrayList<>();
        images.add(new ImageIcon(Objects.requireNonNull(getClass().getResource(DefaultIcon.CHAOS_ORB.path()))).getImage());
        images.add(new ImageIcon(Objects.requireNonNull(getClass().getResource(DefaultIcon.CHAOS_ORB.path()))).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        setIconImages(images);
        JPanel contentPanel = new JPanel(new BorderLayout());
        setContentPane(contentPanel);
        previousButton.setVisible(false);
        contentPanel.add(new BufferPanel(cardPanel, 20), BorderLayout.CENTER);

        JPanel progressPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        int inset = 5;
        int buttonSpacing = 10;
        gc.insets = new Insets(inset, inset, inset, 0);
        progressPanel.add(countLabel, gc);
        buttonPanel.add(previousButton, gc);
        gc.insets.right = inset;
        gc.insets.left = buttonSpacing;
        gc.gridx++;
        buttonPanel.add(nextButton, gc);

        JPanel bufferPanel = new JPanel(new BorderLayout());
        bufferPanel.add(progressPanel, BorderLayout.WEST);
        bufferPanel.add(buttonPanel, BorderLayout.EAST);

        contentPanel.add(bufferPanel, BorderLayout.SOUTH);

        setAlwaysOnTop(true);
        setMinimumSize(new Dimension(300, 200));
        setResizable(false);
        addListeners();
        pack();
        countLabel.setText("");
    }

    private void addListeners() {
        previousButton.addActionListener(e -> {
            cardPanel.previous();
            updateComponents();
        });
        nextButton.addActionListener(e -> {
            if (cardPanel.getCurrentCardIndex() == cardPanel.getComponentCount() - 1) {
                finishSetup();
            } else {
                cardPanel.next();
                updateComponents();
            }
        });
    }

    /**
     * Adds setup panels to the window based on the results of the {@link SetupManager}.
     */
    public void buildSetupCardPanel() {
        cardPanel.add(startPanel);
        for (SetupPhase phase : SetupManager.getSetupPhases()) {
            switch (phase) {
                case GAME_INSTALL_DIRECTORY:
                    addSetupPanel(clientPanel);
                    break;
                case GAME_DETECTION_METHOD:
                    addSetupPanel(gameDetectionPanel);
                    break;
                case STASH_FOLDERS:
                    addSetupPanel(stashFolderPanel);
                    break;
            }
        }
        cardPanel.add(finishPanel);
        pack();
    }

    private void addSetupPanel(AbstractSetupPanel panel) {
        panel.initializeComponents();
        setupPanels.add(panel);
        cardPanel.add(panel);
    }

    private void updateComponents() {
        int cardIndex = cardPanel.getCurrentCardIndex();
        if (cardIndex == 0) nextButton.setEnabled(true);
        previousButton.setVisible(cardPanel.getCurrentCardIndex() > 0);
        if (cardPanel.getCurrentCardIndex() < cardPanel.getComponentCount() - 1) nextButton.setText(NEXT_TEXT);
        else nextButton.setText(FINISH_TEXT);
        countLabel.setText(cardIndex + "/" + setupPanels.size());
        countLabel.setVisible(cardIndex > 0 && cardIndex < cardPanel.getComponentCount() - 1);
        pack();
    }

    private void finishSetup() {
        for (AbstractSetupPanel panel : setupPanels) panel.applyCompletedSetup();
        SaveManager.settingsSaveFile.saveToDisk(false);
        App.launchApp();
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        pack();
        POEWindow.centerWindow(this);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) applyDefaultSizeAndLocation();
    }

}

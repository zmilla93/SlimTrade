package com.slimtrade.gui.setup;

import com.slimtrade.App;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.enums.SetupPhase;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.CardPanel;
import com.slimtrade.gui.managers.SetupManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SetupWindow extends JFrame {

    private final CardPanel cardPanel = new CardPanel();

    private final StartSetupPanel startPanel = new StartSetupPanel();
    private final FinishSetupPanel finishPanel = new FinishSetupPanel();

    private final JButton previousButton = new JButton("Previous");
    public final JButton nextButton = new JButton(NEXT_TEXT);

    private final ClientSetupPanel clientPanel = new ClientSetupPanel();
    private final GameDetectionSetupPanel gameDetectionPanel = new GameDetectionSetupPanel();
    private final LegacyStashSetupPanel stashPanel = new LegacyStashSetupPanel();
    private final StashFolderSetupPanel stashFolderPanel = new StashFolderSetupPanel();

    private static final String NEXT_TEXT = "Next";
    private static final String FINISH_TEXT = "Finish";

    private final JLabel countLabel = new JLabel("10/10");

    private final HashMap<Integer, AbstractSetupPanel> panelMap = new HashMap<>();

    public SetupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SlimTrade Setup");
        // FIXME : Make a window that handles icons automatically so this can be reused?
        ArrayList<Image> images = new ArrayList<>();
        images.add(new ImageIcon(Objects.requireNonNull(getClass().getResource(DefaultIcon.CHAOS_ORB.path()))).getImage());
        images.add(new ImageIcon(Objects.requireNonNull(getClass().getResource(DefaultIcon.CHAOS_ORB.path()))).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        setIconImages(images);
        JPanel contentPanel = new JPanel();
        setContentPane(contentPanel);
        previousButton.setVisible(false);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cardPanel, BorderLayout.CENTER);

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
        setLocationRelativeTo(null);
    }

    /**
     * Adds setup panels to the window based on the results of the {@link SetupManager}.
     */
    public void buildSetupCardPanel() {
        cardPanel.add(startPanel);
        for (SetupPhase phase : SetupManager.getSetupPhases()) {
            switch (phase) {
                case GAME_INSTALL_DIRECTORY:
                    cardPanel.add(clientPanel);
                    break;
                case GAME_DETECTION_METHOD:
                    cardPanel.add(gameDetectionPanel);
                    break;
                case STASH_POSITION:
                    cardPanel.add(stashPanel);
                    break;
                case STASH_FOLDERS:
                    cardPanel.add(stashFolderPanel);
                    break;
            }
        }
        cardPanel.add(finishPanel);
        pack();
        setLocationRelativeTo(null);
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

    private void updateComponents() {
        int cardIndex = cardPanel.getCurrentCardIndex();
        if (cardIndex == 0) nextButton.setEnabled(true);
        previousButton.setVisible(cardPanel.getCurrentCardIndex() > 0);
        if (cardPanel.getCurrentCardIndex() < cardPanel.getComponentCount() - 1) nextButton.setText(NEXT_TEXT);
        else nextButton.setText(FINISH_TEXT);
        countLabel.setText(cardIndex + "/" + (cardPanel.getComponentCount() - 2));
        countLabel.setVisible(cardIndex > 0 && cardIndex < cardPanel.getComponentCount() - 1);
        pack();
    }

    private void finishSetup() {
        SaveManager.settingsSaveFile.saveToDisk(false);
        App.launchApp();
    }

}

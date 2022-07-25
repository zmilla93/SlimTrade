package com.slimtrade.gui.setup;

import com.slimtrade.App;
import com.slimtrade.core.enums.SetupPhase;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.SetupManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SetupWindow extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final StartSetupPanel startPanel = new StartSetupPanel();
    private final FinishSetupPanel finishPanel = new FinishSetupPanel();

    private final JButton previousButton = new JButton("Previous");
    private final JButton nextButton = new JButton(NEXT_TEXT);

    private final ClientSetupPanel clientPanel = new ClientSetupPanel(nextButton);
    private final CharacterSetupPanel characterPanel = new CharacterSetupPanel(nextButton);
    private final StashSetupPanel stashPanel = new StashSetupPanel(nextButton);

    private static final String NEXT_TEXT = "Next";

    private final JPanel contentPanel = new JPanel();
    private final JLabel countLabel = new JLabel();

    public static final int VERTICAL_INSET = 20;
    public static final int HORIZONTAL_INSET = 20;
    public static final Insets OUTER_INSETS = new Insets(VERTICAL_INSET, HORIZONTAL_INSET, VERTICAL_INSET, HORIZONTAL_INSET);
    private HashMap<Integer, AbstractSetupPanel> panelMap = new HashMap<>();

    private int panelIndex;

    public SetupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SlimTrade Setup");
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
        setMinimumSize(new Dimension(200, 200));
        setResizable(false);
        addListeners();
        pack();
        setLocationRelativeTo(null);
    }

    private void addListeners() {
        previousButton.addActionListener(e -> {
            if (panelIndex > 0) {
                panelIndex--;
                showIndexedPanel();
                nextButton.setEnabled(true);
            }
        });

        nextButton.addActionListener(e -> {
            if (panelIndex == cardPanel.getComponentCount() - 1) {
                finishSetup();
            }
            if (panelIndex < cardPanel.getComponentCount() - 1) {
                panelIndex++;
                showIndexedPanel();
            }
        });
    }

    private void finishSetup() {
        if (panelMap.values().size() > 0) {
            if (characterPanel.isSetupValid())
                SaveManager.settingsSaveFile.data.characterName = characterPanel.getCharacterName();
            if (clientPanel.isSetupValid())
                SaveManager.settingsSaveFile.data.clientPath = clientPanel.getClientPath();
            SaveManager.settingsSaveFile.saveToDisk(false);
        }
        App.launchApp();
    }

    private void showIndexedPanel() {
        AbstractSetupPanel panel = panelMap.get(panelIndex);
        if (panel != null) panel.validateNextButton();
        cardLayout.show(cardPanel, Integer.toString(panelIndex));
        previousButton.setVisible(panelIndex != 0);
        if (panelIndex < cardPanel.getComponentCount() - 1) nextButton.setText(NEXT_TEXT);
        else nextButton.setText("Finish");
        countLabel.setText(panelIndex + "/" + (cardPanel.getComponentCount() - 2));
        countLabel.setVisible(panelIndex > 0 && panelIndex < cardPanel.getComponentCount() - 1);
    }

    public void setup() {
        cardPanel.add(startPanel, Integer.toString(cardPanel.getComponentCount()));
        for (SetupPhase phase : SetupManager.getSetupPhases()) {
            switch (phase) {
                case CLIENT_PATH:
                    panelMap.put(cardPanel.getComponentCount(), clientPanel);
                    cardPanel.add(clientPanel, Integer.toString(cardPanel.getComponentCount()));
                    break;
                case CHARACTER_NAME:
                    panelMap.put(cardPanel.getComponentCount(), characterPanel);
                    cardPanel.add(characterPanel, Integer.toString(cardPanel.getComponentCount()));
                    break;
                case STASH_POSITION:
                    panelMap.put(cardPanel.getComponentCount(), stashPanel);
                    cardPanel.add(stashPanel, Integer.toString(cardPanel.getComponentCount()));
                    break;
            }
        }
        cardPanel.add(finishPanel, Integer.toString(cardPanel.getComponentCount()));
        pack();
        setLocationRelativeTo(null);
    }

    public StashSetupPanel getStashPanel() {
        return stashPanel;
    }

}

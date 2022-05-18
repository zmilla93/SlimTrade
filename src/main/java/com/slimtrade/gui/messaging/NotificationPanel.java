package com.slimtrade.gui.messaging;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.colortheme.components.ColorPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationPanel extends ColorPanel {

    // Panels
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final ColorPanel borderPanel = new ColorPanel(new GridBagLayout());

    private NotificationButton playerNamePanel = new NotificationButton("Player Name");
    private JPanel pricePanel = new JPanel();
    private NotificationButton itemPanel = new NotificationButton("Item Name");

    private final JPanel topPanel = new JPanel(new GridBagLayout());
    private final JPanel bottomPanel = new JPanel(new BorderLayout());
    private final JPanel topButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel bottomButtonPanel = new JPanel(new GridBagLayout());
    private final ColorPanel timerPanel = new ColorPanel(new BorderLayout());
    private final JLabel timerLabel = new JLabel("12s");

    protected ArrayList<MacroButton> topMacros = new ArrayList<>();
    protected ArrayList<MacroButton> bottomMacros = new ArrayList<>();

    public NotificationPanel() {

        // Border Setup
        JPanel topContainer = new JPanel(new BorderLayout());
        JPanel bottomContainer = new JPanel(new BorderLayout());
        borderPanel.setBackground(Color.ORANGE);
        setLayout(new GridBagLayout());
//        add(borderPanel, BorderLayout.CENTER);
//        borderPanel.add(mainPanel, BorderLayout.CENTER);
        int inset = 2;
        Insets insets = new Insets(inset, inset, inset, inset);
        GridBagConstraints gc = ZUtil.getGC();
        gc.insets = new Insets(2, 2, 2, 2);
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        add(borderPanel, gc);
        borderPanel.add(mainPanel, gc);
//        ZUtil.addBorderStruts(this, insets);
//        ZUtil.addBorderStruts(borderPanel, insets);

        topPanel.setBackground(Color.RED);

        // Main Panel

        mainPanel.setBackground(Color.GREEN);
        mainPanel.add(topContainer, BorderLayout.NORTH);
        mainPanel.add(Box.createHorizontalStrut(400), BorderLayout.CENTER);
        mainPanel.add(bottomContainer, BorderLayout.SOUTH);

        // Containers
        topContainer.add(topPanel, BorderLayout.CENTER);
        topContainer.add(topButtonPanel, BorderLayout.EAST);
        bottomContainer.add(bottomPanel, BorderLayout.CENTER);
        bottomContainer.add(bottomButtonPanel, BorderLayout.EAST);

        bottomPanel.setBackground(Color.GREEN);
        bottomButtonPanel.setBackground(Color.cyan);

        topContainer.setBackground(Color.RED);
        topButtonPanel.setBackground(Color.YELLOW);

        gc = ZUtil.getGC();
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;


        // Top Panel
        gc.weightx = 0.7f;
        topPanel.add(playerNamePanel, gc);
        gc.gridx++;
        gc.weightx = 0.3f;
        topPanel.add(pricePanel, gc);

        // Bottom Panel
        bottomPanel.add(timerPanel, BorderLayout.WEST);
        bottomPanel.add(itemPanel, BorderLayout.CENTER);

        int timerInset = 4;
        timerPanel.add(Box.createHorizontalStrut(timerInset), BorderLayout.WEST);
        timerPanel.add(Box.createHorizontalStrut(timerInset), BorderLayout.EAST);

        setBackgroundKey("Separator.background");
        borderPanel.setBackground(new Color(67, 138, 59));
        playerNamePanel.setBackgroundKey("Panel.background");
        itemPanel.setBackgroundKey("ComboBox.background");
        timerPanel.setBackgroundKey("ComboBox.background");
        pricePanel.setBackground(new Color(67, 138, 59));
        timerPanel.colorMultiplier = 1.1f;
        pricePanel.add(new JLabel("Price"));
        timerPanel.add(timerLabel, BorderLayout.CENTER);

        //  Call setup, which should be overwritten
        setup();


        revalidate();
    }

    public void setup() {
        // FIXME: TEMP SETUP
        topMacros.addAll(Arrays.asList(SaveManager.settingsSaveFile.data.incomingMacroButtons));
        bottomMacros.addAll(Arrays.asList(SaveManager.settingsSaveFile.data.outgoingMacroButtons));
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        for (MacroButton macro : topMacros) {
            JButton button = new NotificationIconButton(macro.icon.path);
            button.updateUI();
            topButtonPanel.add(button, gc);
            gc.gridx++;
        }
        for (MacroButton macro : bottomMacros) {
            JButton button = new NotificationIconButton(macro.icon.path);
            bottomButtonPanel.add(button, gc);
            gc.gridx++;
        }
    }

    public void resize() {
//        System.out.println(getPreferredSize().height);
//        System.out.println("H" + getSize().height);
//        setPreferredSize(null);
//
//        setPreferredSize(new Dimension(400, getPreferredSize().height));
//        System.out.println("H" + getSize().height);
//        revalidate();

    }

    public void buildPanel() {

    }

}

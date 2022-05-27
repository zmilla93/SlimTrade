package com.slimtrade.gui.messaging;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.*;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.colortheme.components.ColorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NotificationPanel extends ColorPanel {

    // Panels
    protected final NotificationButton playerNameButton = new NotificationButton("Player Name");
    protected final CurrencyPanel pricePanel = new CurrencyPanel();
    protected final JLabel priceLabel = new JLabel("Price");
    protected final CurrencyPanel itemButton = new CurrencyPanel();
    private final ColorPanel borderPanel = new ColorPanel(new GridBagLayout());
    private final ColorPanel timerPanel = new ColorPanel(new BorderLayout());

    private final JPanel topButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel bottomButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel topPanel;

    private final JLabel timerLabel = new JLabel("0s");
    private JButton closeButton;

    protected float namePanelWeight = 0.7f;
    protected float pricePanelWeight = 0.3f;

    protected ArrayList<MacroButton> topMacros = new ArrayList<>();
    protected ArrayList<MacroButton> bottomMacros = new ArrayList<>();

    protected boolean createListeners;

    protected TradeOffer tradeOffer;
    private boolean playerJoinedArea;

    protected Color messageColor = new Color(60, 173, 173, 255);

    // Timers
    private Timer timer;
    private boolean minuteSwitch = false;
    private int secondCount;
    private int minuteCount;

    public NotificationPanel() {
        this(true);
    }

    public NotificationPanel(boolean createListeners) {
        this.createListeners = createListeners;
        // Panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topContainer = new JPanel(new BorderLayout());
        JPanel bottomContainer = new JPanel(new BorderLayout());
        topPanel = new JPanel(new GridBagLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());


        // Border Setup
        borderPanel.setBackground(Color.ORANGE);
        setLayout(new GridBagLayout());
        int inset = 2;
        GridBagConstraints gc = ZUtil.getGC();
        gc.insets = new Insets(2, 2, 2, 2);
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        add(borderPanel, gc);
        borderPanel.add(mainPanel, gc);


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

        // TEMP
        topPanel.setBackground(Color.YELLOW);
        bottomPanel.setBackground(Color.RED);
        bottomContainer.setBackground(Color.RED);
        bottomButtonPanel.setBackground(Color.RED);
        mainPanel.setBackground(Color.YELLOW);

        // Top Panel
        gc = ZUtil.getGC();
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = namePanelWeight;
        topPanel.add(playerNameButton, gc);
        gc.gridx++;
        gc.weightx = pricePanelWeight;
        topPanel.add(pricePanel, gc);

        // Bottom Panel
//        pricePanel.add(priceLabel);
        bottomPanel.add(timerPanel, BorderLayout.WEST);
        bottomPanel.add(itemButton, BorderLayout.CENTER);
        int timerInset = 4;
        timerPanel.add(Box.createHorizontalStrut(timerInset), BorderLayout.WEST);
        timerPanel.add(Box.createHorizontalStrut(timerInset), BorderLayout.EAST);
        timerPanel.add(timerLabel, BorderLayout.CENTER);

        revalidate();
    }

    /**
     * Should be called after properties are applied.
     */
    public void setup() {
        GridBagConstraints gc = addMacrosToPanel(topButtonPanel, topMacros);
        closeButton = new NotificationIconButton("/icons/default/closex64.png");
        topButtonPanel.add(closeButton, gc);
        addMacrosToPanel(bottomButtonPanel, bottomMacros);

        // Colors
        setBackgroundKey("Separator.background");
        playerNameButton.setBackgroundKey("Panel.background");
        itemButton.setBackgroundKey("ComboBox.background");
        timerPanel.colorMultiplier = 1.1f;
        timerPanel.setBackgroundKey("ComboBox.background");


        ColorManager.recursiveUpdateUI(this);
        if (createListeners)
            addListeners();
    }

    private GridBagConstraints addMacrosToPanel(JPanel panel, ArrayList<MacroButton> macros) {
        panel.removeAll();
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        for (MacroButton macro : macros) {
            JButton button = new NotificationIconButton(macro.icon.path);
            button.updateUI();
            panel.add(button, gc);
            if (createListeners) {
                button.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1 && !macro.lmbResponse.isBlank()) {
                            POEInterface.pasteWithFocus(macro.lmbResponse, tradeOffer);
                        }
                        if (e.getButton() == MouseEvent.BUTTON3 && !macro.rmbResponse.isBlank()) {
                            POEInterface.pasteWithFocus(macro.rmbResponse, tradeOffer);
                        }
                    }
                });
            }
            gc.gridx++;
        }
        return gc;
    }

    private void addListeners() {
        NotificationPanel self = this;
        closeButton.addActionListener(e -> FrameManager.messageManager.removeMessage(self));
        startTimer();
    }

    public void startTimer() {
        if (timer != null) timer.stop();
        secondCount = 0;
        minuteCount = 1;
        timer = new Timer(1000, e -> procTimer());
        timer.start();
    }

    private void procTimer() {
        if (minuteSwitch) {
            minuteCount++;
            timerLabel.setText(minuteCount + "m");
        } else {
            secondCount++;
            timerLabel.setText(secondCount + "s");
            if (secondCount == 60) {
                timerLabel.setText(1 + "m");
                minuteSwitch = true;
                timer.stop();
                timer = new Timer(60000, e -> procTimer());
                timer.start();
            }
        }
    }

    public void applyMessageColor() {
        borderPanel.setBackground(messageColor);
        pricePanel.setBackground(messageColor);
        topPanel.setBackground(messageColor);
        if (playerJoinedArea) {
            playerNameButton.setForeground(messageColor);
            timerLabel.setForeground(messageColor);
            itemButton.setForeground(messageColor);
        }
    }

    public void setPlayerJoinedArea() {
        playerJoinedArea = true;
        applyMessageColor();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    /**
     * Called when a message is removed from the message manager.
     */
    public void cleanup() {
        // Override this!
    }

}

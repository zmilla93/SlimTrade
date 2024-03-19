package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.enums.MacroButtonType;
import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.core.hotkeys.IHotkeyAction;
import com.slimtrade.core.hotkeys.NotificationPanelHotkey;
import com.slimtrade.core.managers.FontManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.BorderlessButton;
import com.slimtrade.gui.components.CurrencyLabelFactory;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.theme.ThemeManager;
import com.slimtrade.modules.theme.components.ColorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationPanel extends ColorPanel {

    // Panels
    protected final NotificationButton playerNameButton = new NotificationButton("Player Name");
    protected final JPanel pricePanel = new JPanel(new GridBagLayout());
    protected final BorderlessButton itemButton = new BorderlessButton();
    protected final ColorPanel borderPanel = new ColorPanel(new GridBagLayout());
    protected final ColorPanel timerPanel = new ColorPanel(new BorderLayout());

    private final JPanel topButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel bottomButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel topPanel;
    protected final JPanel bottomContainer = new JPanel(new BorderLayout());
    private Component bottomVerticalStrut;

    private final JLabel timerLabel = new JLabel("0s");
    protected JButton closeButton;

    protected float namePanelWeight = 0.7f;
    protected float pricePanelWeight = 0.3f;

    protected ArrayList<MacroButton> topMacros = new ArrayList<>();
    protected ArrayList<MacroButton> bottomMacros = new ArrayList<>();

    protected boolean createListeners;

    protected TradeOffer tradeOffer;
    protected PasteReplacement pasteReplacement;
    private boolean playerJoinedArea;

    protected Color messageColor = new Color(60, 173, 173, 255);
    protected Color currencyTextColor;

    // Timers
    private Timer timer;
    private boolean minuteSwitch = false;
    private int secondCount;
    private int minuteCount;

    private HashMap<HotkeyData, IHotkeyAction> hotkeyMap = new HashMap<>();

    public NotificationPanel() {
        this(true);
    }

    public NotificationPanel(boolean createListeners) {
        this.createListeners = createListeners;
        // Panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topContainer = new JPanel(new BorderLayout());

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
//        mainPanel.add(Box.createHorizontalStrut(400), BorderLayout.CENTER);
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

        setWidth(SaveManager.overlaySaveFile.data.messageWidth);
    }

    /**
     * Should be called after properties are applied.
     */
    public void setup() {
        GridBagConstraints gc = addMacrosToPanel(topButtonPanel, topMacros);
        closeButton = new NotificationIconButton(DefaultIcon.CLOSE.path());
        topButtonPanel.add(closeButton, gc);
        addMacrosToPanel(bottomButtonPanel, bottomMacros);

        // Colors
        setBackgroundKey("Separator.background");
        playerNameButton.setBackgroundKey("Panel.background");
        itemButton.setBackgroundKey("ComboBox.background");
        timerPanel.setColorMultiplier(1.1f);
        timerPanel.setBackgroundKey("ComboBox.background");
        ThemeManager.recursiveUpdateUI(this);
        resizeStrut();
        if (createListeners)
            addListeners();
        updateSize();
        FontManager.applyFont(playerNameButton);
    }

    private GridBagConstraints addMacrosToPanel(JPanel panel, ArrayList<MacroButton> macros) {
        panel.removeAll();
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        NotificationPanel self = this;
        hotkeyMap.clear();
        for (MacroButton macro : macros) {
            JButton button;
            if (macro.buttonType == MacroButtonType.ICON) {
                button = new NotificationIconButton(macro.icon.path());
            } else {
                button = new NotificationButton(macro.text);
                ((NotificationButton) button).setHorizontalInset(4);
            }
            button.updateUI();
            panel.add(button, gc);
            if (createListeners) {
                if (!hotkeyMap.containsKey(macro.hotkeyData))
                    hotkeyMap.put(macro.hotkeyData, new NotificationPanelHotkey(macro, this, pasteReplacement));
                button.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!ZUtil.isEmptyString(macro.lmbResponse)) {
                                System.out.println("mc:" + macro.lmbResponse);
                                POEInterface.runCommand(macro.lmbResponse, pasteReplacement);
//                                if (macro.lmbResponse.contains("/invite")) onInvite();
                            }
//                            if (macro.close) FrameManager.messageManager.removeMessage(self);
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            if (!ZUtil.isEmptyString(macro.rmbResponse))
                                POEInterface.runCommand(macro.rmbResponse, pasteReplacement);
//                            if (macro.close) FrameManager.messageManager.removeMessage(self);
                        }
                        handleHotkeyMutual(macro);
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

    public void setWidth(int width) {
        setMinimumSize(new Dimension(width, 0));
        setMaximumSize(new Dimension(width, 10000));
        setPreferredSize(null);
        setPreferredSize(new Dimension(width, getPreferredSize().height));
        revalidate();
    }

    protected void addPlayerButtonListener(String playerName) {
        playerNameButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    POEInterface.pasteWithFocus("/whois " + playerName);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    POEInterface.pasteWithFocus("@" + playerName, true);
                }
            }
        });
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
        pricePanel.setForeground(currencyTextColor);
        if (playerJoinedArea) {
            playerNameButton.setForeground(messageColor);
            timerLabel.setForeground(messageColor);
            itemButton.setForeground(messageColor);
            CurrencyLabelFactory.applyColorToLabel(itemButton, messageColor);
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

    public void resizeStrut() {
        if (bottomVerticalStrut != null) bottomContainer.remove(bottomVerticalStrut);
        bottomVerticalStrut = Box.createVerticalStrut(closeButton.getPreferredSize().height);
        bottomContainer.add(bottomVerticalStrut, BorderLayout.WEST);
    }

    public void updateSize() {
        resizeStrut();
        setWidth(SaveManager.overlaySaveFile.data.messageWidth);
    }

    public void checkHotkeys(HotkeyData hotkeyData) {
        IHotkeyAction action = hotkeyMap.get(hotkeyData);
        if (action != null) action.execute();
    }

    public void handleHotkeyMutual(MacroButton macro) {
        if (macro.lmbResponse.contains("/invite")) onInvite();
        if (macro.close) FrameManager.messageManager.removeMessage(this);
    }

    /**
     * Called when a button is pressed that uses the /invite command.
     */
    protected void onInvite() {
        // Overwrite this!
    }

    /**
     * Called when a message is removed from the message manager.
     */
    public void cleanup() {
        timer.stop();
        // Override this, but call super!
    }

}

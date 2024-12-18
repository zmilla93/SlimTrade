package github.zmilla93.gui.messaging;

import github.zmilla93.core.data.PasteReplacement;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.enums.MacroButtonType;
import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.core.hotkeys.HotkeyData;
import github.zmilla93.core.hotkeys.IHotkeyAction;
import github.zmilla93.core.hotkeys.NotificationPanelHotkey;
import github.zmilla93.core.managers.FontManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.BorderlessButton;
import github.zmilla93.gui.components.poe.ThemePanel;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.theme.components.ColorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Extend this for every unique type of panel added to the MessageManager.
 */
public abstract class NotificationPanel extends ColorPanel {

    // Components
    protected final NotificationButton playerNameButton = new NotificationButton("Placeholder Player Name");
    protected final JPanel pricePanel;
    protected final BorderlessButton itemButton = new BorderlessButton();
    protected final ColorPanel timerPanel = new ColorPanel(new BorderLayout());
    protected final JButton closeButton = new NotificationIconButton(DefaultIcon.CLOSE);
    private final JLabel timerLabel = new JLabel("0s");
    protected boolean closeButtonInTopRow = true;

    // Container Panels
    protected final JPanel borderPanel;
    private final JPanel topPanel;
    private final JPanel bottomPanel;
    private final JPanel topButtonPanel;
    private final JPanel bottomButtonPanel;
    protected final JPanel topContainer = new JPanel(new BorderLayout());
    protected final JPanel bottomContainer = new JPanel(new BorderLayout());
    private Component bottomVerticalStrut;

    private static final float NAME_PANEL_WEIGHT = 0.7f;
    private static final float PRICE_PANEL_WEIGHT = 0.3f;

    protected ArrayList<MacroButton> topMacros = new ArrayList<>();
    protected ArrayList<MacroButton> bottomMacros = new ArrayList<>();

    protected PasteReplacement pasteReplacement;
    protected boolean createListeners;
    private boolean playerJoinedArea;

    protected Color messageColor = new Color(60, 173, 173, 255);
    protected Color currencyTextColor;
    protected final ThemeColor themeColor;

    // Timer
    private Timer timer;
    private boolean minuteSwitch = false;
    private int secondCount;
    private int minuteCount;

    private final HashMap<HotkeyData, IHotkeyAction> hotkeyMap = new HashMap<>();

    public NotificationPanel(ThemeColor themeColor) {
        this(themeColor, true);
    }

    public NotificationPanel(ThemeColor themeColor, boolean createListeners) {
        this.themeColor = themeColor;
        this.createListeners = createListeners;
        // Panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        borderPanel = new ThemePanel(themeColor, new GridBagLayout());
        pricePanel = new ThemePanel(themeColor, new GridBagLayout());
        topPanel = new ThemePanel(themeColor, new GridBagLayout());
        topButtonPanel = new ThemePanel(themeColor, new GridBagLayout());
        bottomPanel = new ThemePanel(themeColor, new BorderLayout());
        bottomButtonPanel = new ThemePanel(themeColor, new GridBagLayout());

        // Border Setup
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.insets = new Insets(2, 2, 2, 2);
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        add(borderPanel, gc);
        borderPanel.add(mainPanel, gc);

        // Main Panel
        mainPanel.add(topContainer, BorderLayout.NORTH);
        mainPanel.add(bottomContainer, BorderLayout.SOUTH);

        // Containers
        topContainer.add(topPanel, BorderLayout.CENTER);
        topContainer.add(topButtonPanel, BorderLayout.EAST);
        bottomContainer.add(bottomPanel, BorderLayout.CENTER);
        bottomContainer.add(bottomButtonPanel, BorderLayout.EAST);

        // Top Panel
        gc = ZUtil.getGC();
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = NAME_PANEL_WEIGHT;
        topPanel.add(playerNameButton, gc);
        gc.gridx++;
        gc.weightx = PRICE_PANEL_WEIGHT;
        topPanel.add(pricePanel, gc);

        // Bottom Panel
        bottomPanel.add(timerPanel, BorderLayout.WEST);
        bottomPanel.add(itemButton, BorderLayout.CENTER);
        int timerInset = 4;
        timerPanel.add(Box.createHorizontalStrut(timerInset), BorderLayout.WEST);
        timerPanel.add(Box.createHorizontalStrut(timerInset), BorderLayout.EAST);
        timerPanel.add(timerLabel, BorderLayout.CENTER);

        // Colors
        setBackgroundKey("Separator.background");
        playerNameButton.setBackgroundKey("Panel.background");
        itemButton.setBackgroundKey("ComboBox.background");
        timerPanel.setColorMultiplier(1.1f);
        timerPanel.setBackgroundKey("ComboBox.background");
    }

    /**
     * Classes that extend NotificationPanel must call setup after completing their constructor.
     */
    public void setup() {
        // Add buttons
        GridBagConstraints topGC = addMacrosToPanel(topButtonPanel, topMacros);
        GridBagConstraints bottomGC = addMacrosToPanel(bottomButtonPanel, bottomMacros);
        if (closeButtonInTopRow) topButtonPanel.add(closeButton, topGC);
        else bottomButtonPanel.add(closeButton, bottomGC);

        updateUI();
        if (createListeners) addListeners();
    }

    private GridBagConstraints addMacrosToPanel(JPanel panel, ArrayList<MacroButton> macros) {
        panel.removeAll();
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        hotkeyMap.clear();
        for (MacroButton macro : macros) {
            JButton button;
            if (macro.buttonType == MacroButtonType.ICON) {
                button = new NotificationIconButton(macro.icon);
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
                                POEInterface.runCommand(macro.lmbResponse, pasteReplacement);
                            }
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            if (!ZUtil.isEmptyString(macro.rmbResponse))
                                POEInterface.runCommand(macro.rmbResponse, pasteReplacement);
                        }
                        handleHotkeyMutual(macro);
                    }
                });
            }
            gc.gridx++;
        }
        return gc;
    }

    protected void addListeners() {
        closeButton.addActionListener(e -> FrameManager.messageManager.removeMessage(NotificationPanel.this));
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
                    POEInterface.pasteWithFocus("@" + playerName + " ", true);
                }
            }
        });
    }

    public void startTimer() {
        if (timer != null) timer.stop();
        secondCount = 0;
        minuteCount = 1;
        timer = new Timer(1000, e -> incrementTimer());
        timer.start();
    }

    private void incrementTimer() {
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
                timer = new Timer(60000, e -> incrementTimer());
                timer.start();
            }
        }
    }

    protected abstract void resolveMessageColor();

    public void applyMessageColor() {
        if (borderPanel == null) return;
        // FIXME : Need to fix player joined area.
//        if (playerJoinedArea) {
//            playerNameButton.setForeground(messageColor);
//            timerLabel.setForeground(messageColor);
//            itemButton.setForeground(messageColor);
//            CurrencyLabelFactory.applyColorToLabel(itemButton, messageColor);
//        }
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

    public void resizeStrut() {
        if (closeButton == null) return;
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
        // Override this!
    }

    /**
     * Called when a message is removed from the message manager.
     */
    public void cleanup() {
        timer.stop();
        timer = null;
        // Override this, but call super!
    }

    @Override
    public void updateUI() {
        super.updateUI();
        updateSize();
        applyMessageColor();
        // FIXME:
        FontManager.applyFont(playerNameButton);
    }

}

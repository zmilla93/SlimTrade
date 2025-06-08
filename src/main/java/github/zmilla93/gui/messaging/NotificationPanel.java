package github.zmilla93.gui.messaging;

import github.zmilla93.core.data.PasteReplacement;
import github.zmilla93.core.enums.ButtonRow;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.enums.MacroButtonType;
import github.zmilla93.core.hotkeys.HotkeyData;
import github.zmilla93.core.hotkeys.IHotkeyAction;
import github.zmilla93.core.hotkeys.NotificationPanelHotkey;
import github.zmilla93.core.managers.AudioManager;
import github.zmilla93.core.managers.FontManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.CurrencyLabelFactory;
import github.zmilla93.gui.components.poe.ThemeLabel;
import github.zmilla93.gui.components.poe.ThemePanel;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.theme.ThemeColor;
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
    protected final ThemePanel pricePanel;
    protected final NotificationButton itemButton = new NotificationButton();
    protected final ThemePanel timerPanel = new ThemePanel(ThemeColor.BUTTON_BACKGROUND, new BorderLayout());
    protected final JButton closeButton = new NotificationIconButton(DefaultIcon.CLOSE);
    private final JLabel timerLabel = new ThemeLabel(ThemeColor.BUTTON_FOREGROUND, "0s");
    private final ButtonRow closeButtonRow;

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

    protected ArrayList<MacroButton> macros = new ArrayList<>();
    @Deprecated
    protected ArrayList<MacroButton> topMacros = new ArrayList<>();
    @Deprecated
    protected ArrayList<MacroButton> bottomMacros = new ArrayList<>();

    protected PasteReplacement pasteReplacement;
    protected boolean createListeners;
    private boolean playerJoinedArea;

    protected final ThemeColor messageColor;

    // Timer
    private Timer timer;
    private boolean minuteSwitch = false;
    private int secondCount;
    private int minuteCount;

    private final HashMap<HotkeyData, IHotkeyAction> hotkeyMap = new HashMap<>();

    public NotificationPanel(ThemeColor messageColor, ArrayList<MacroButton> macros) {
        this(messageColor, macros, ButtonRow.TOP_ROW);
    }

    public NotificationPanel(ThemeColor messageColor, ArrayList<MacroButton> macros, ButtonRow closeButtonRow) {
        this(messageColor, macros, closeButtonRow, true);
    }

    public NotificationPanel(ThemeColor messageColor, ArrayList<MacroButton> macros, boolean createListeners) {
        this(messageColor, macros, ButtonRow.TOP_ROW, createListeners);
    }

    public NotificationPanel(ThemeColor messageColor, ArrayList<MacroButton> macros, ButtonRow closeButtonRow, boolean createListeners) {
        this.messageColor = messageColor;
        this.macros = macros;
        this.closeButtonRow = closeButtonRow;
        this.createListeners = createListeners;
        // Panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        borderPanel = new ThemePanel(messageColor, new GridBagLayout());
        pricePanel = new ThemePanel(messageColor, new GridBagLayout());
        topPanel = new ThemePanel(messageColor, new GridBagLayout());
        topButtonPanel = new ThemePanel(messageColor, new GridBagLayout());
        bottomPanel = new ThemePanel(messageColor, new BorderLayout());
        bottomButtonPanel = new ThemePanel(messageColor, new GridBagLayout());
        pricePanel.useHighContrastTextColor(true);

        // Border Setup
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        int borderSize = 2;
        gc.insets = new Insets(borderSize, borderSize, borderSize, borderSize);
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
//        timerPanel.setBackgroundKey("HelpButton.background");
        setup();
    }

    /**
     * Classes that extend NotificationPanel must call setup after completing their constructor.
     */
    protected void setup() {
        GridBagConstraints gc = addMacroButtons();
        if (closeButtonRow == ButtonRow.TOP_ROW) topButtonPanel.add(closeButton, gc);
        else if (closeButtonRow == ButtonRow.BOTTOM_ROW) bottomButtonPanel.add(closeButton, gc);
        updateUI();
        if (createListeners) addListeners();
    }

    private GridBagConstraints addMacroButtons() {
        topButtonPanel.removeAll();
        bottomButtonPanel.removeAll();
        //
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        if (macros == null) return gc;
        for (MacroButton macro : macros) {
            JButton button;
            if (macro.buttonType == MacroButtonType.ICON) {
                button = new NotificationIconButton(macro.icon);
            } else {
                button = new NotificationButton(macro.text);
                ((NotificationButton) button).setHorizontalInset(4);
            }
            if (createListeners) createMacroButtonListener(button, macro);
            if (macro.row == ButtonRow.TOP_ROW) topButtonPanel.add(button, gc);
            else if (macro.row == ButtonRow.BOTTOM_ROW) bottomButtonPanel.add(button, gc);
            gc.gridx++;
        }
        return gc;
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
            if (createListeners) createMacroButtonListener(button, macro);
            gc.gridx++;
        }
        return gc;
    }

    private void createMacroButtonListener(JButton button, MacroButton macro) {
        /// Button listener
        button.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) POEInterface.runCommand(macro.lmbResponse, pasteReplacement);
                if (e.getButton() == MouseEvent.BUTTON3) POEInterface.runCommand(macro.rmbResponse, pasteReplacement);
                handleHotkeyMutual(macro);
            }
        });
        /// Register the button's hotkey
        if (macro.hotkeyData != null && !hotkeyMap.containsKey(macro.hotkeyData) && pasteReplacement != null)
            hotkeyMap.put(macro.hotkeyData, new NotificationPanelHotkey(macro, this, pasteReplacement));
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

    // FIXME : Use result label so this only needs to be called once
    public void applyPlayerJoinedAreaColor() {
        if (borderPanel == null) return;
        // FIXME : Need to fix player joined area.
        if (playerJoinedArea) {
            playerNameButton.setForeground(messageColor.current());
            timerLabel.setForeground(messageColor.current());
            CurrencyLabelFactory.applyColorToLabel(itemButton, messageColor.current());
        }
    }

    public void setPlayerJoinedAreaColor() {
        playerJoinedArea = true;
        applyPlayerJoinedAreaColor();
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

    public void markPlayerJoined() {
//        private fun handlePlayerJoinedArea(panel: NotificationPanel) {
        if (playerJoinedArea) return;
        AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.playerJoinedAreaSound);
        setPlayerJoinedAreaColor();
    }

    /**
     * Called when a button is pressed that uses the /invite command.
     */
    protected void onInvite() {
        /// Override this!
    }

    /**
     * Called when a message is removed from the message manager. Make sure to call super!
     */
    public void cleanup() {
        /// Override this, but call super!
        // FIXME : Timer is null when it shouldn't be? Might be calling cleanup twice.
        if (timer != null) timer.stop();
        timer = null;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        updateSize();
        applyPlayerJoinedAreaColor();
        // FIXME:
        FontManager.applyFont(playerNameButton);
    }

}

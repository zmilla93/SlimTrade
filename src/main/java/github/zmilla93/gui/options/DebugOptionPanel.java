package github.zmilla93.gui.options;

import github.zmilla93.App;
import github.zmilla93.core.data.PlayerMessage;
import github.zmilla93.core.jna.JnaAwtEvent;
import github.zmilla93.core.managers.FontManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.windows.test.MessageTestWindow;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.testing.UIColorKeyViewer;
import github.zmilla93.modules.theme.testing.UIManagerInspectorWindow;

import javax.swing.*;
import java.awt.*;

public class DebugOptionPanel extends AbstractOptionPanel {

    private static final boolean GROUP_FONTS_BY_FAMILY = true;

    private final HotkeyButton chatHotkeyButton = new HotkeyButton();

    // Game Specific
    private final JButton incomingMessageButtonPoe1 = new JButton("Incoming Trade");
    private final JButton outgoingMessageButtonPoe1 = new JButton("Outgoing Trade");
    private final JButton clientButtonPoe1 = new JButton("Open Client.txt");

    private final JButton incomingMessageButtonPoe2 = new JButton("Incoming Trade");
    private final JButton outgoingMessageButtonPoe2 = new JButton("Outgoing Trade");
    private final JButton clientButtonPoe2 = new JButton("Open Client.txt");
    // Shared
    private final JButton scannerMessageButton = new JButton("Scanner Message");
    private final JButton updateMessageButton = new JButton("Update Message");
    private final JButton createBackupButton = new JButton("Create Backup");
    private final JButton loadBackupButton = new JButton("Load Backup");
    private final JButton uiDumpButton = new JButton("Dump UIManager to Clipboard");
    private final JComboBox<String> fontCombo = new JComboBox<>();

    // Debug Buttons
    private final JButton messageTestButton = new JButton("Message Test");
    private final JButton themeInspectorButton = new JButton("Theme Inspector");
    private final JButton uiKeyViewerButton = new JButton("UI Key Viewer");

    private static final String slashString = "Slash - // \\\\";
    private final JLabel chineseLabel = new JLabel(FontManager.CHINESE_EXAMPLE_TEXT);
    private final JLabel japaneseLabel = new JLabel(FontManager.JAPANESE_EXAMPLE_TEXT);
    private final JLabel koreanLabel = new JLabel(FontManager.KOREAN_EXAMPLE_TEXT);
    private final JLabel russianLabel = new JLabel(FontManager.RUSSIAN_EXAMPLE_TEXT);
    private final JLabel thaiLabel = new JLabel(FontManager.THAI_EXAMPLE_TEXT);
    private final JLabel slashLabel = new JLabel(slashString);
    private final JLabel[] translationLabels = new JLabel[]{chineseLabel, japaneseLabel, koreanLabel, russianLabel, thaiLabel, slashLabel};

    public DebugOptionPanel() {
        chatHotkeyButton.addHotkeyChangeListener(e -> {
            if (chatHotkeyButton.getData() != null) {
                System.out.println(chatHotkeyButton.getData().toString());
                JnaAwtEvent.hotkeyToEvent(chatHotkeyButton.getData());
            }
        });


        addHeader("Path of Exile Stuff");
        addComponent(createButtonPanel());
        addHeader("Debug Stuff");
        addComponent(createDebugButtonPanel());
        addVerticalStrut();
        addHeader("Hotkey Test");
        addComponent(chatHotkeyButton);
        addVerticalStrut();
        addHeader("Font Test");
        addComponent(new StyledLabel("Almost before we knew it, we had left the ground."));
        addComponent(new StyledLabel("The quick brown fox jumped over the lazy dogs.").bold());
        addComponent(new StyledLabel("You are captured, stupid beast!"));
        addComponent(new StyledLabel("You are captured, stupid beast!").bold());

        // Font Translations
        addComponent(fontCombo);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (GROUP_FONTS_BY_FAMILY) for (String s : ge.getAvailableFontFamilyNames()) fontCombo.addItem(s);
        else for (Font font : ge.getAllFonts()) fontCombo.addItem(font.getName());
        addComponent(createFontTranslationPanel());

        addListeners();
    }

    private void addListeners() {
        incomingMessageButtonPoe1.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE)));
        incomingMessageButtonPoe2.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE, Game.PATH_OF_EXILE_2)));
        outgoingMessageButtonPoe1.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE)));
        outgoingMessageButtonPoe2.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE, Game.PATH_OF_EXILE_2)));
        scannerMessageButton.addActionListener(e -> FrameManager.messageManager.addScannerMessage(new ChatScannerEntry("alch"), new PlayerMessage("CoolTrader123", "wtb alch for chaos")));
        updateMessageButton.addActionListener(e -> FrameManager.messageManager.addUpdateMessage(true, App.getAppInfo().appVersion.toString()));
        uiDumpButton.addActionListener(e -> ThemeManager.debugKeyValueDump());
        clientButtonPoe1.addActionListener(e -> ZUtil.openFile(SaveManager.settingsSaveFile.data.settingsPoe1.getClientPath()));
        clientButtonPoe2.addActionListener(e -> ZUtil.openFile(SaveManager.settingsSaveFile.data.settingsPoe2.getClientPath()));
        fontCombo.addActionListener(e -> {
            String s = (String) fontCombo.getSelectedItem();
            Font font = new Font(s, Font.PLAIN, 12);
            for (JLabel label : translationLabels) {
                label.setFont(font);
            }
            revalidate();
            repaint();
        });
        createBackupButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(FrameManager.optionsWindow, "Are you sure you want to create a new backup of all app settings?\nThis will delete the existing backup.", "Create Backup", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.OK_OPTION) SaveManager.createBackup();
        });
        loadBackupButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(FrameManager.optionsWindow, "Are you sure you want to load the existing settings backup?\nThis requires a program restart after.", "Load Backup", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.OK_OPTION) SaveManager.loadBackup();
        });
        themeInspectorButton.addActionListener(e -> FrameManager.uiManagerInspectorWindow = (UIManagerInspectorWindow) FrameManager.showLazyDebugWindow(FrameManager.uiManagerInspectorWindow, UIManagerInspectorWindow.class));
        uiKeyViewerButton.addActionListener(e -> FrameManager.uiColorKeyViewer = (UIColorKeyViewer) FrameManager.showLazyDebugWindow(FrameManager.uiColorKeyViewer, UIColorKeyViewer.class));
        messageTestButton.addActionListener(e -> FrameManager.debugMessageWindow = (MessageTestWindow) FrameManager.showLazyDebugWindow(FrameManager.debugMessageWindow, MessageTestWindow.class));
    }

    private JPanel createDebugButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(themeInspectorButton, gc);
        gc.gridy++;
        panel.add(uiKeyViewerButton, gc);
        gc.gridy++;
        panel.add(messageTestButton, gc);
        gc.gridy++;
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        buttonPanel.add(new JLabel(Game.PATH_OF_EXILE_1.explicitName), gc);
        gc.gridx++;
        buttonPanel.add(new JLabel(Game.PATH_OF_EXILE_2.explicitName), gc);
        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        // POE1 Buttons
        buttonPanel.add(incomingMessageButtonPoe1, gc);
        gc.gridy++;
        buttonPanel.add(outgoingMessageButtonPoe1, gc);
        gc.gridy++;
        buttonPanel.add(clientButtonPoe1, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        // POE2 Buttons
        buttonPanel.add(incomingMessageButtonPoe2, gc);
        gc.gridy++;
        buttonPanel.add(outgoingMessageButtonPoe2, gc);
        gc.gridy++;
        buttonPanel.add(clientButtonPoe2, gc);
        gc.gridy++;
        // Mutual Buttons
        gc.gridx = 0;
        gc.gridwidth = 2;
        buttonPanel.add(scannerMessageButton, gc);
        gc.gridy++;
        buttonPanel.add(updateMessageButton, gc);
        gc.gridy++;
        buttonPanel.add(uiDumpButton, gc);
        gc.gridy++;
        gc.gridwidth = 1;
        buttonPanel.add(createBackupButton, gc);
        gc.gridx++;
        buttonPanel.add(loadBackupButton, gc);
//        buttonPanel.add(new ComponentPanel(0, createBackupButton, loadBackupButton), gc);
        return buttonPanel;

    }

    private JPanel createFontTranslationPanel() {
        JPanel panel = new JPanel();

        AbstractOptionPanel p1 = new AbstractOptionPanel();
        p1.addHeader("DEFAULT FONT");
        p1.addComponent(new JLabel(FontManager.CHINESE_EXAMPLE_TEXT));
        p1.addComponent(new JLabel(FontManager.JAPANESE_EXAMPLE_TEXT));
        p1.addComponent(new JLabel(FontManager.KOREAN_EXAMPLE_TEXT));
        p1.addComponent(new JLabel(FontManager.THAI_EXAMPLE_TEXT));
        p1.addComponent(new JLabel(FontManager.RUSSIAN_EXAMPLE_TEXT));
        p1.addComponent(new JLabel(slashString));

        AbstractOptionPanel p2 = new AbstractOptionPanel();
        p2.addHeader("TRANSLATED FONTS");
        p2.addComponent(FontManager.applyFont(new JLabel(FontManager.CHINESE_EXAMPLE_TEXT)));
        p2.addComponent(FontManager.applyFont(new JLabel(FontManager.JAPANESE_EXAMPLE_TEXT)));
        p2.addComponent(FontManager.applyFont(new JLabel(FontManager.KOREAN_EXAMPLE_TEXT)));
        p2.addComponent(FontManager.applyFont(new JLabel(FontManager.THAI_EXAMPLE_TEXT)));
        p2.addComponent(FontManager.applyFont(new JLabel(FontManager.RUSSIAN_EXAMPLE_TEXT)));
        p2.addComponent(new JLabel(slashString));

        AbstractOptionPanel p3 = new AbstractOptionPanel();
        p3.addHeader("FONT PREVIEW");
        p3.addComponent(chineseLabel);
        p3.addComponent(japaneseLabel);
        p3.addComponent(koreanLabel);
        p3.addComponent(russianLabel);
        p3.addComponent(thaiLabel);
        p3.addComponent(slashLabel);

        panel.setLayout(new GridBagLayout());
//        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.VERTICAL;
        gc.weighty = 1;
        panel.add(p1, gc);
        gc.gridx++;
        panel.add(p2, gc);
        gc.gridx++;
        panel.add(p3, gc);

        return panel;
    }

}

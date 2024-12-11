package github.zmilla93.gui.options;

import github.zmilla93.core.data.PlayerMessage;
import github.zmilla93.core.jna.JnaAwtEvent;
import github.zmilla93.core.managers.FontManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class DebugOptionPanel extends AbstractOptionPanel {

    private static final boolean GROUP_FONTS_BY_FAMILY = true;

    private final HotkeyButton chatHotkeyButton = new HotkeyButton();

    private final JButton incomingMessageButton = new JButton("Incoming Trade");
    private final JButton outgoingMessageButton = new JButton("Outgoing Trade");
    private final JButton scannerMessageButton = new JButton("Scanner Message");
    private final JButton updateMessageButton = new JButton("Update Message");
    private final JButton createBackupButton = new JButton("Create New Backup");
    private final JButton loadBackupButton = new JButton("Load Backup");
    private final JButton clientButton = new JButton("Open Client.txt");
    private final JButton uiDumpButton = new JButton("Dump UIManager to Clipboard");
    private final JComboBox<String> fontCombo = new JComboBox<>();

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

        addHeader("Debug Tools");
        addComponent(incomingMessageButton);
        addComponent(outgoingMessageButton);
        addComponent(scannerMessageButton);
        addComponent(updateMessageButton);
        addComponent(clientButton);
        addComponent(uiDumpButton);
        addComponent(new ComponentPanel(createBackupButton, loadBackupButton));
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
        incomingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE)));
        outgoingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE)));
        scannerMessageButton.addActionListener(e -> FrameManager.messageManager.addScannerMessage(new ChatScannerEntry("alch"), new PlayerMessage("CoolTrader123", "wtb alch for chaos")));
        updateMessageButton.addActionListener(e -> FrameManager.messageManager.addUpdateMessage(true));
        uiDumpButton.addActionListener(e -> ThemeManager.debugKeyValueDump());
        clientButton.addActionListener(e -> ZUtil.openFile(SaveManager.settingsSaveFile.data.clientPath));
        fontCombo.addActionListener(e -> {
            String s = (String) fontCombo.getSelectedItem();
            Font font = new Font(s, Font.PLAIN, 12);
            for (JLabel label : translationLabels) {
                label.setFont(font);
            }
            revalidate();
            repaint();
        });
        createBackupButton.addActionListener(e -> SaveManager.createBackup());
        loadBackupButton.addActionListener(e -> SaveManager.loadBackup());
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

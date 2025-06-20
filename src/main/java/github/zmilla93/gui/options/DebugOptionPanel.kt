package github.zmilla93.gui.options

import github.zmilla93.App
import github.zmilla93.core.data.PlayerMessage
import github.zmilla93.core.debug.DebugUtil
import github.zmilla93.core.jna.JnaAwtEvent
import github.zmilla93.core.managers.FontManager
import github.zmilla93.core.managers.FontManager.applyFont
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.Game
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.core.trading.TradeOfferType
import github.zmilla93.core.utility.ZUtil.getGC
import github.zmilla93.core.utility.ZUtil.openFile
import github.zmilla93.gui.chatscanner.ChatScannerEntry
import github.zmilla93.gui.components.HotkeyButton
import github.zmilla93.gui.components.StyledLabel
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.gui.windows.test.MessageTestWindow
import github.zmilla93.modules.theme.ThemeManager.debugKeyValueDump
import github.zmilla93.modules.theme.testing.UIColorKeyViewer
import github.zmilla93.modules.theme.testing.UIManagerInspectorWindow
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class DebugOptionPanel : AbstractOptionPanel() {
    private val chatHotkeyButton = HotkeyButton()

    // Game Specific
    private val incomingMessageButtonPoe1 = JButton("Incoming Trade")
    private val incomingClientMessageButtonPoe1 = JButton("Incoming Client").onClick {
        DebugUtil.incomingTrade(Game.PATH_OF_EXILE_1)
    }
    private val outgoingMessageButtonPoe1 = JButton("Outgoing Trade")
    private val clientButtonPoe1 = JButton("Open Client.txt")

    private val incomingMessageButtonPoe2 = JButton("Incoming Trade")
    private val incomingClientMessageButtonPoe2 = JButton("Incoming Client").onClick {
        DebugUtil.incomingTrade(Game.PATH_OF_EXILE_2)
    }
    private val outgoingMessageButtonPoe2 = JButton("Outgoing Trade")
    private val clientButtonPoe2 = JButton("Open Client.txt")

    // Shared
    private val scannerMessageButton = JButton("Scanner Message")
    private val updateMessageButton = JButton("Update Message")
    private val createBackupButton = JButton("Create Backup")
    private val loadBackupButton = JButton("Load Backup")
    private val uiDumpButton = JButton("Dump UIManager to Clipboard")
    private val fontCombo = JComboBox<String?>()

    // Debug Buttons
    private val messageTestButton = JButton("Message Test")
    private val themeInspectorButton = JButton("Theme Inspector")
    private val uiKeyViewerButton = JButton("UI Key Viewer")

    private val chineseLabel = JLabel(FontManager.CHINESE_EXAMPLE_TEXT)
    private val japaneseLabel = JLabel(FontManager.JAPANESE_EXAMPLE_TEXT)
    private val koreanLabel = JLabel(FontManager.KOREAN_EXAMPLE_TEXT)
    private val russianLabel = JLabel(FontManager.RUSSIAN_EXAMPLE_TEXT)
    private val thaiLabel = JLabel(FontManager.THAI_EXAMPLE_TEXT)
    private val slashLabel: JLabel = JLabel(slashString)
    private val translationLabels =
        arrayOf<JLabel>(chineseLabel, japaneseLabel, koreanLabel, russianLabel, thaiLabel, slashLabel)

    init {
        chatHotkeyButton.addHotkeyChangeListener {
            if (chatHotkeyButton.data != null) {
                println(chatHotkeyButton.data.toString())
                JnaAwtEvent.hotkeyToEvent(chatHotkeyButton.data)
            }
        }

        addHeader("Path of Exile Stuff")
        addComponent(createButtonPanel())
        addHeader("Debug Stuff")
        addComponent(createDebugButtonPanel())
        addVerticalStrut()
        addHeader("Hotkey Test")
        addComponent(chatHotkeyButton)
        addVerticalStrut()
        addHeader("Font Test")
        addComponent(StyledLabel("Almost before we knew it, we had left the ground."))
        addComponent(StyledLabel("The quick brown fox jumped over the lazy dogs.").bold())
        addComponent(StyledLabel("You are captured, stupid beast!"))
        addComponent(StyledLabel("You are captured, stupid beast!").bold())

        // Font Translations
        addComponent(fontCombo)
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        if (GROUP_FONTS_BY_FAMILY) for (s in ge.getAvailableFontFamilyNames()) fontCombo.addItem(s)
        else for (font in ge.getAllFonts()) fontCombo.addItem(font.getName())
        addComponent(createFontTranslationPanel())

        addListeners()
    }

    private fun addListeners() {
        incomingMessageButtonPoe1.addActionListener {
            FrameManager.messageManager.addMessage(
                TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE)
            )
        }
        incomingMessageButtonPoe2.addActionListener {
            FrameManager.messageManager.addMessage(
                TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE, Game.PATH_OF_EXILE_2)
            )
        }
        outgoingMessageButtonPoe1.addActionListener {
            FrameManager.messageManager.addMessage(
                TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE)
            )
        }
        outgoingMessageButtonPoe2.addActionListener {
            FrameManager.messageManager.addMessage(
                TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE, Game.PATH_OF_EXILE_2)
            )
        }
        scannerMessageButton.addActionListener {
            FrameManager.messageManager.addScannerMessage(
                ChatScannerEntry("alch"),
                PlayerMessage("CoolTrader123", "wtb alch for chaos")
            )
        }
        updateMessageButton.addActionListener {
            FrameManager.messageManager.addUpdateMessage(
                true,
                App.getAppInfo().appVersion.toString()
            )
        }
        uiDumpButton.addActionListener { debugKeyValueDump() }
        clientButtonPoe1.addActionListener { openFile(SaveManager.settingsSaveFile.data.settingsPoe1.clientPath) }
        clientButtonPoe2.addActionListener { openFile(SaveManager.settingsSaveFile.data.settingsPoe2.clientPath) }
        fontCombo.addActionListener {
            val s = fontCombo.selectedItem as String
            val font = Font(s, Font.PLAIN, 12)
            for (label in translationLabels) {
                label.setFont(font)
            }
            revalidate()
            repaint()
        }
        createBackupButton.addActionListener {
            val response = JOptionPane.showConfirmDialog(
                FrameManager.optionsWindow,
                "Are you sure you want to create a new backup of all app settings?\nThis will delete the existing backup.",
                "Create Backup",
                JOptionPane.YES_NO_OPTION
            )
            if (response == JOptionPane.OK_OPTION) SaveManager.createBackup()
        }
        loadBackupButton.addActionListener {
            val response = JOptionPane.showConfirmDialog(
                FrameManager.optionsWindow,
                "Are you sure you want to load the existing settings backup?\nThis requires a program restart after.",
                "Load Backup",
                JOptionPane.YES_NO_OPTION
            )
            if (response == JOptionPane.OK_OPTION) SaveManager.loadBackup()
        }
        themeInspectorButton.addActionListener {
            FrameManager.uiManagerInspectorWindow = FrameManager.showLazyDebugWindow(
                FrameManager.uiManagerInspectorWindow,
                UIManagerInspectorWindow::class.java
            ) as UIManagerInspectorWindow?
        }
        uiKeyViewerButton.addActionListener {
            FrameManager.uiColorKeyViewer = FrameManager.showLazyDebugWindow(
                FrameManager.uiColorKeyViewer,
                UIColorKeyViewer::class.java
            ) as UIColorKeyViewer?
        }
        messageTestButton.addActionListener {
            FrameManager.debugMessageWindow = FrameManager.showLazyDebugWindow(
                FrameManager.debugMessageWindow,
                MessageTestWindow::class.java
            ) as MessageTestWindow
        }
    }

    private fun createDebugButtonPanel(): JPanel {
        val panel = JPanel(GridBagLayout())
        val gc = getGC()
        gc.weightx = 1.0
        gc.fill = GridBagConstraints.HORIZONTAL
        panel.add(themeInspectorButton, gc)
        gc.gridy++
        panel.add(uiKeyViewerButton, gc)
        gc.gridy++
        panel.add(messageTestButton, gc)
        gc.gridy++
        return panel
    }

    private fun createButtonPanel(): JPanel {
        val buttonPanel = JPanel(GridBagLayout())
        val gc = getGC()
        buttonPanel.add(JLabel(Game.PATH_OF_EXILE_1.explicitName), gc)
        gc.gridx++
        buttonPanel.add(JLabel(Game.PATH_OF_EXILE_2.explicitName), gc)
        gc.gridx = 0
        gc.gridy = 1
        gc.fill = GridBagConstraints.HORIZONTAL
        gc.weightx = 1.0
        // POE1 Buttons
        buttonPanel.add(incomingMessageButtonPoe1, gc)
        gc.gridy++
        buttonPanel.add(outgoingMessageButtonPoe1, gc)
        gc.gridy++
        buttonPanel.add(incomingClientMessageButtonPoe1, gc)
        gc.gridy++
        buttonPanel.add(clientButtonPoe1, gc)
        gc.gridx = 1
        gc.gridy = 1
        // POE2 Buttons
        buttonPanel.add(incomingMessageButtonPoe2, gc)
        gc.gridy++
        buttonPanel.add(outgoingMessageButtonPoe2, gc)
        gc.gridy++
        buttonPanel.add(incomingClientMessageButtonPoe2, gc)
        gc.gridy++
        buttonPanel.add(clientButtonPoe2, gc)
        gc.gridy++
        // Mutual Buttons
        gc.gridx = 0
        gc.gridwidth = 2
        buttonPanel.add(scannerMessageButton, gc)
        gc.gridy++
        buttonPanel.add(updateMessageButton, gc)
        gc.gridy++
        buttonPanel.add(uiDumpButton, gc)
        gc.gridy++
        gc.gridwidth = 1
        buttonPanel.add(createBackupButton, gc)
        gc.gridx++
        buttonPanel.add(loadBackupButton, gc)
        //        buttonPanel.add(new ComponentPanel(0, createBackupButton, loadBackupButton), gc);
        return buttonPanel
    }

    private fun createFontTranslationPanel(): JPanel {
        val panel = JPanel()

        val p1 = AbstractOptionPanel()
        p1.addHeader("DEFAULT FONT")
        p1.addComponent(JLabel(FontManager.CHINESE_EXAMPLE_TEXT))
        p1.addComponent(JLabel(FontManager.JAPANESE_EXAMPLE_TEXT))
        p1.addComponent(JLabel(FontManager.KOREAN_EXAMPLE_TEXT))
        p1.addComponent(JLabel(FontManager.THAI_EXAMPLE_TEXT))
        p1.addComponent(JLabel(FontManager.RUSSIAN_EXAMPLE_TEXT))
        p1.addComponent(JLabel(slashString))

        val p2 = AbstractOptionPanel()
        p2.addHeader("TRANSLATED FONTS")
        p2.addComponent(applyFont(JLabel(FontManager.CHINESE_EXAMPLE_TEXT)))
        p2.addComponent(applyFont(JLabel(FontManager.JAPANESE_EXAMPLE_TEXT)))
        p2.addComponent(applyFont(JLabel(FontManager.KOREAN_EXAMPLE_TEXT)))
        p2.addComponent(applyFont(JLabel(FontManager.THAI_EXAMPLE_TEXT)))
        p2.addComponent(applyFont(JLabel(FontManager.RUSSIAN_EXAMPLE_TEXT)))
        p2.addComponent(JLabel(slashString))

        val p3 = AbstractOptionPanel()
        p3.addHeader("FONT PREVIEW")
        p3.addComponent(chineseLabel)
        p3.addComponent(japaneseLabel)
        p3.addComponent(koreanLabel)
        p3.addComponent(russianLabel)
        p3.addComponent(thaiLabel)
        p3.addComponent(slashLabel)

        panel.setLayout(GridBagLayout())
        //        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));
        val gc = getGC()
        gc.fill = GridBagConstraints.VERTICAL
        gc.weighty = 1.0
        panel.add(p1, gc)
        gc.gridx++
        panel.add(p2, gc)
        gc.gridx++
        panel.add(p3, gc)

        return panel
    }

    companion object {
        private const val GROUP_FONTS_BY_FAMILY = true

        private const val slashString = "Slash - // \\\\"
    }
}

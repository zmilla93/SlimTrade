package github.zmilla93.gui.managers

import github.zmilla93.App
import github.zmilla93.core.chatparser.ChatScannerListener
import github.zmilla93.core.chatparser.PlayerJoinedAreaListener
import github.zmilla93.core.chatparser.TradeListener
import github.zmilla93.core.data.IgnoreItemData
import github.zmilla93.core.data.PlayerMessage
import github.zmilla93.core.enums.ExpandDirection
import github.zmilla93.core.enums.MatchType
import github.zmilla93.core.event.ChatScannerEvent
import github.zmilla93.core.event.PlayerJoinedAreaEvent
import github.zmilla93.core.event.TradeEvent
import github.zmilla93.core.hotkeys.HotkeyData
import github.zmilla93.core.managers.AudioManager
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.core.trading.TradeOfferType
import github.zmilla93.core.utility.AdvancedMouseListener
import github.zmilla93.core.utility.TradeUtil
import github.zmilla93.gui.chatscanner.ChatScannerEntry
import github.zmilla93.gui.components.CustomTabbedPane
import github.zmilla93.gui.messaging.*
import github.zmilla93.gui.windows.BasicDialog
import github.zmilla93.modules.saving.ISaveListener
import github.zmilla93.modules.theme.ThemeManager
import github.zmilla93.modules.theme.listeners.IFontChangeListener
import github.zmilla93.modules.updater.ZLogger
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.swing.*
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * This window displays all popup notifications.
 *
 * @see NotificationPanel
 *
 * @see TradeMessagePanel
 *
 * @see ChatScannerMessagePanel
 *
 * @see UpdateMessagePanel
 */
class MessageManager : BasicDialog(), TradeListener, ChatScannerListener, PlayerJoinedAreaListener, IFontChangeListener,
    ISaveListener {
    private val gc: GridBagConstraints
    private val messageContainer: JPanel
    private val messageWrapperPanel: JPanel
    private val tabbedPane = CustomTabbedPane()
    private val cardLayout = CardLayout()
    private var currentlyUsingTabs: Boolean

    // Expanding
    private var expanded = false
    private val expandPanel: ExpandPanel

    // Opacity
    private var mouseHover = false
    private val fadeTimer: Timer
    private var fadedOpacity = 0f
    private var targetOpacity = 0f
    private val fadeRunnable: Runnable
    private var fading = false

    init {
        setBackground(ThemeManager.TRANSPARENT)

        contentPanel.setLayout(cardLayout)
        // FIXME : Tabbed pane probably doesn't update background on theme change
        contentPanel.setBackground(UIManager.getColor("Panel.background"))
        messageContainer = JPanel(GridBagLayout())
        messageContainer.setBackground(ThemeManager.TRANSPARENT)
        messageWrapperPanel = JPanel(BorderLayout())
        messageWrapperPanel.add(messageContainer, BorderLayout.CENTER)
        messageWrapperPanel.setBackground(ThemeManager.TRANSPARENT)

        contentPanel.add(messageWrapperPanel, DEFAULT_KEY)
        contentPanel.add(tabbedPane, TAB_KEY)
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT)

        // Init GridBagLayout
        gc = GridBagConstraints()
        gc.gridx = 0
        gc.gridy = 0
        gc.insets = if (this.isExpandUp) Insets(MESSAGE_GAP, 0, 0, 0) else Insets(0, 0, MESSAGE_GAP, 0)
        gc.weightx = 1.0
        gc.fill = GridBagConstraints.HORIZONTAL

        // Expand Panel
        expandPanel = ExpandPanel()
        fadeTimer = Timer(0, ActionListener { e: ActionEvent? ->
            fadeToOpacity(fadedOpacity)
            stopFadeTimer()
        })

        // Fade Runnable
        fadeRunnable = Runnable {
            fading = true
            while (fading && opacity != targetOpacity) {
                val curOpacity = opacity
                val step: Float = if (targetOpacity > curOpacity) OPACITY_STEP else -OPACITY_STEP
                var newOpacity = curOpacity + step
                if (abs(targetOpacity - newOpacity) <= OPACITY_STEP) newOpacity = targetOpacity
                val finalOpacity = TradeUtil.floatWithinRange(newOpacity, 0f, 1f)
                SwingUtilities.invokeLater { opacity = finalOpacity }
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        setMinimumSize(null)
        currentlyUsingTabs = SaveManager.settingsSaveFile.data.useMessageTabs
        setDisplayMode(SaveManager.settingsSaveFile.data.useMessageTabs)
        refreshFadeData()
        refresh()
        addListeners()
        ThemeManager.addFontListener(this)
        SaveManager.settingsSaveFile.addListener(this)
        App.chatParser.tradeListeners.add(this)
        App.chatParser.chatScannerListeners.add(this)
        App.chatParser.playerJoinedAreaListeners.add(this)
        App.parserEvent.subscribe(TradeEvent::class.java) { handleTrade(it) }
        App.parserEvent.subscribe(PlayerJoinedAreaEvent::class.java) { onJoinedArea(it) }
        App.parserEvent.subscribe(ChatScannerEvent::class.java) { onScannerMessage(it) }
        //        App.parserEvent.subscribe(TradeEvent.class, );
//        parser.addTradeListener(FrameManager.messageManager)
//        parser.addChatScannerListener(FrameManager.messageManager)
//        parser.addJoinedAreaListener(FrameManager.messageManager)
    }

    private fun addListeners() {
        expandPanel.button.setAllowedMouseButtons(1)
        expandPanel.button.addMouseListener(object : AdvancedMouseListener() {
            override fun click(e: MouseEvent?) {
                expanded = !expanded
                if (expanded) expandMessages()
                else collapseMessages()
            }
        })
        tabbedPane.addMouseListener(object : AdvancedMouseListener() {
            override fun click(e: MouseEvent) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    val index = tabbedPane.indexAtLocation(e.getX(), e.getY())
                    if (index < 0) return
                    removeMessage((tabbedPane.getComponentAt(index) as NotificationPanel?)!!)
                }
            }
        })
    }

    // FIXME: Should merge this with addMessage
    fun addUpdateMessage(playSound: Boolean, tag: String?) {
        assert(SwingUtilities.isEventDispatchThread())
        if (playSound) AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.updateSound)
        val panel = UpdateMessagePanel(tag)
        addMessageMutual(panel)
    }

    @JvmOverloads
    fun addMessage(tradeOffer: TradeOffer, playSound: Boolean = true, force: Boolean = false) {
        assert(SwingUtilities.isEventDispatchThread())
        setIgnoreRepaint(true)
        if (this.messageCount > 20) return
        if (!force) {
            if (!SaveManager.settingsSaveFile.data.enableIncomingTrades && tradeOffer.offerType == TradeOfferType.INCOMING_TRADE) return
            if (!SaveManager.settingsSaveFile.data.enableOutgoingTrades && tradeOffer.offerType == TradeOfferType.OUTGOING_TRADE) return
        }
        if (playSound) {
            when (tradeOffer.offerType) {
                TradeOfferType.INCOMING_TRADE -> {
                    val sound = AudioManager.getPriceThresholdSound(
                        tradeOffer.priceName,
                        floor(tradeOffer.priceQuantity).toInt()
                    )
                    if (sound == null) {
                        AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.incomingSound)
                    } else {
                        AudioManager.playSoundComponent(sound)
                    }
                }

                TradeOfferType.OUTGOING_TRADE -> AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.outgoingSound)
                TradeOfferType.CHAT_SCANNER_MESSAGE -> AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.chatScannerSound)
                else -> {}
            }
        }
        val panel = TradeMessagePanel(tradeOffer)
        addMessageMutual(panel)
        //        panel.resizeStrut();
    }

    @JvmOverloads
    fun addScannerMessage(entry: ChatScannerEntry?, playerMessage: PlayerMessage?, playSound: Boolean = true) {
        val panel = ChatScannerMessagePanel(entry, playerMessage)
        if (playSound) AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.chatScannerSound)
        addMessageMutual(panel)
    }

    fun addKalguurMessage() {
        AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.kalguurSound)
        addMessageMutual(KalguurMessagePanel())
    }

    private fun addMessageMutual(panel: NotificationPanel) {
        addPanel(panel)
        if (this.messageCount == 1) {
            opacity = 1f
            startFadeTimer()
        }
        revalidate()
        refresh()
    }

    private fun recheckMessageCollapse() {
        if (SaveManager.settingsSaveFile.data.useMessageTabs) return
        for (i in 0..<messageContainer.componentCount) {
            val comp = messageContainer.getComponent(i)
            if (SaveManager.settingsSaveFile.data.collapseMessages && !expanded && i >= SaveManager.settingsSaveFile.data.messageCountBeforeCollapse) comp.isVisible =
                false
            else comp.isVisible = true
        }
        if (SaveManager.settingsSaveFile.data.collapseMessages && messageContainer.componentCount > SaveManager.settingsSaveFile.data.messageCountBeforeCollapse) {
            expandPanel.isVisible = true
        } else {
            expandPanel.isVisible = false
            expanded = false
        }
        updateExpandText()
    }

    private fun addPanel(panel: NotificationPanel) {
        if (SaveManager.settingsSaveFile.data.useMessageTabs) {
            tabbedPane.addTab((tabbedPane.tabCount + 1).toString(), panel)
        } else {
            gc.gridy =
                if (this.isExpandUp) 9999 - messageContainer.componentCount else messageContainer.componentCount
            messageContainer.add(panel, gc)
        }
    }

    fun removeMessage(panel: NotificationPanel) {
        assert(SwingUtilities.isEventDispatchThread())
        setIgnoreRepaint(true)
        panel.cleanup()
        messageContainer.remove(panel)
        tabbedPane.remove(panel)
        refresh()
    }

    private fun refreshOrder() {
        if (SaveManager.settingsSaveFile.data.useMessageTabs) return
        val components = messageContainer.components
        messageContainer.removeAll()
        for (comp in components) {
            addPanel((comp as NotificationPanel?)!!)
        }
        messageWrapperPanel.remove(expandPanel)
        if (this.isExpandUp) messageWrapperPanel.add(expandPanel, BorderLayout.NORTH)
        else messageWrapperPanel.add(expandPanel, BorderLayout.SOUTH)
    }

    /**
     * Closes all outgoing trades except for the panel being passed in.
     */
    fun quickCloseOutgoing(panel: NotificationPanel?) {
        setIgnoreRepaint(true)
        for (i in this.messageCount - 1 downTo 0) {
            val comp: Component = getMessageComponent(i)
            if (comp !is TradeMessagePanel) continue
            val otherPanel = comp
            val trade = otherPanel.tradeOffer
            if (trade.offerType == TradeOfferType.OUTGOING_TRADE && comp !== panel) removeMessage(otherPanel)
        }
        refresh()
    }

    /**
     * Closes all trade offers with the same name and price as the trade offer passed in.
     */
    fun quickCloseIncoming(targetOffer: TradeOffer) {
        setIgnoreRepaint(true)
        for (i in this.messageCount - 1 downTo 0) {
            val comp: Component = getMessageComponent(i)
            if (comp !is TradeMessagePanel) continue
            val panel = comp
            val trade = panel.tradeOffer
            if (trade.offerType == TradeOfferType.INCOMING_TRADE && trade.itemName == targetOffer.itemName
                && trade.priceName == targetOffer.priceName
                && trade.priceQuantity == targetOffer.priceQuantity
            ) {
                removeMessage(panel)
            }
        }
        refresh()
    }

    /**
     * Closes all incoming trades that match the given criteria.
     */
    fun quickCloseIgnore(item: IgnoreItemData) {
        setIgnoreRepaint(true)
        for (i in this.messageCount - 1 downTo 0) {
            val comp: Component = getMessageComponent(i)
            if (comp !is TradeMessagePanel) continue
            val panel = comp
            val trade = comp.tradeOffer
            if (trade.offerType != TradeOfferType.INCOMING_TRADE) continue
            if (item.matchType == MatchType.EXACT_MATCH) {
                if (trade.itemName == item.itemName) removeMessage(panel)
            } else if (item.matchType == MatchType.CONTAINS_TEXT) {
                if (trade.itemNameLower.contains(item.itemNameLower())) removeMessage(panel)
            }
        }
        refresh()
    }

    fun closeOldestTrade() {
        val componentCount = this.messageCount
        if (componentCount < 1) return
        val panel = getMessageComponent(0)
        removeMessage(panel)
    }

    private fun expandMessages() {
        for (comp in messageContainer.components) {
            comp.isVisible = true
        }
        updateExpandText()
        refresh()
    }

    private fun collapseMessages() {
        for (i in 0..<messageContainer.componentCount) {
            if (i >= SaveManager.settingsSaveFile.data.messageCountBeforeCollapse) {
                messageContainer.getComponent(i).isVisible = false
            }
        }
        updateExpandText()
        refresh()
    }

    fun changeMessageTab(change: Int) {
        assert(SwingUtilities.isEventDispatchThread())
        if (change != -1 && change != 1) {
            ZLogger.err("Changing message tab expects either -1 or 1.")
            return
        }
        if (!SaveManager.settingsSaveFile.data.useMessageTabs) return
        val selectedTab = tabbedPane.selectedIndex
        if (selectedTab < 0) return
        val nextTab = selectedTab + change
        if (nextTab < 0 || nextTab >= tabbedPane.tabCount) return
        tabbedPane.setSelectedIndex(nextTab)
    }

    private fun updateExpandText() {
        if (expanded) {
            expandPanel.setText("Collapse Messages")
        } else {
            val hiddenMessageCount =
                messageContainer.componentCount - SaveManager.settingsSaveFile.data.messageCountBeforeCollapse
            val suffix = if (hiddenMessageCount > 1) "s" else ""
            expandPanel.setText("+$hiddenMessageCount More Message$suffix")
        }
    }

    private fun startFadeTimer() {
        if (!SaveManager.settingsSaveFile.data.fadeMessages) return
        fadeTimer.stop()
        fadeTimer.restart()
        fadeTimer.start()
    }

    private fun stopFadeTimer() {
        fadeTimer.stop()
    }

    fun refreshFadeData() {
        fadeTimer.initialDelay = (SaveManager.settingsSaveFile.data.secondsBeforeFading * 1000).roundToInt()
        fadedOpacity = SaveManager.settingsSaveFile.data.fadedOpacity / 100f
    }

    private fun fadeToOpacity(opacity: Float) {
        targetOpacity = opacity
        executor.execute(fadeRunnable)
    }

    private val messageComponents: Array<Component?>?
        get() = if (SaveManager.settingsSaveFile.data.useMessageTabs) tabbedPane.getTabbedComponents() else messageContainer.components

    private fun getMessageComponent(index: Int): NotificationPanel {
        return (if (SaveManager.settingsSaveFile.data.useMessageTabs) tabbedPane.getComponentAt(index) else messageContainer.getComponent(
            index
        )) as NotificationPanel
    }

    private val messageCount: Int
        get() = if (SaveManager.settingsSaveFile.data.useMessageTabs) tabbedPane.tabCount else messageContainer.componentCount

    /**
     * Makes sure the MessageManager is in the correct location, in the right order, and all messages have correct visibility.
     */
    fun refresh() {
        assert(SwingUtilities.isEventDispatchThread())
        for (component in messageContainer.components) {
            (component as NotificationPanel).updateSize()
        }
        if (SaveManager.settingsSaveFile.data.useMessageTabs) {
            setVisible(tabbedPane.tabCount != 0)
            for (i in 0..<tabbedPane.tabCount) {
                tabbedPane.setTitleAt(i, (i + 1).toString())
                val comp = tabbedPane.getComponentAt(i)
                (comp as NotificationPanel).updateSize()
            }
        } else {
            setVisible(true)
        }
        expandPanel.updateSize()
        recheckMessageCollapse()
        refreshOrder()
        revalidate()
        pack()
        TradeUtil.applyAnchorPoint(
            this,
            SaveManager.overlaySaveFile.data.messageLocation,
            SaveManager.overlaySaveFile.data.messageExpandDirection
        )
        repaint()
    }

    fun checkMouseHover(p: Point) {
        if (bufferedBounds.contains(p)) {
            if (!mouseHover) {
                mouseHover = true
                fadeToOpacity(1f)
                stopFadeTimer()
            }
        } else {
            if (mouseHover) {
                mouseHover = false
                startFadeTimer()
            }
        }
    }

    fun checkHotkey(hotkeyData: HotkeyData?) {
        if (this.messageCount == 0) return
        if (SaveManager.settingsSaveFile.data.useMessageTabs) {
            val panel = tabbedPane.getSelectedComponent() as NotificationPanel
            panel.checkHotkeys(hotkeyData)
        } else {
            val panel = messageContainer.getComponent(0) as NotificationPanel
            panel.checkHotkeys(hotkeyData)
        }
    }

    private val isExpandUp: Boolean
        get() = SaveManager.overlaySaveFile.data.messageExpandDirection == ExpandDirection.UPWARDS

    private fun setDisplayMode(useTabs: Boolean) {
        assert(SwingUtilities.isEventDispatchThread())
        val components: Array<Component>
        if (currentlyUsingTabs) components = tabbedPane.getTabbedComponents()
        else components = messageContainer.components
        messageContainer.removeAll()
        tabbedPane.removeAll()

        for (comp in components) {
            addPanel((comp as NotificationPanel?)!!)
        }

        contentPanel.setOpaque(useTabs)
        if (useTabs) {
            cardLayout.show(contentPanel, TAB_KEY)
        } else {
            cardLayout.show(contentPanel, DEFAULT_KEY)
        }
        refresh()
        revalidate()
        repaint()
        currentlyUsingTabs = SaveManager.settingsSaveFile.data.useMessageTabs
    }

    private fun handlePlayerJoinedArea(panel: NotificationPanel) {
        AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.playerJoinedAreaSound)
        panel.setPlayerJoinedAreaColor()
    }

    fun handleTrade(event: TradeEvent) {
        if (!event.isLoaded) return
        SwingUtilities.invokeLater { addMessage(event.tradeOffer) }
    }

    @Deprecated("switch to event")
    override fun handleTrade(tradeOffer: TradeOffer, loaded: Boolean) {
        if (!loaded) return
        SwingUtilities.invokeLater { addMessage(tradeOffer) }
    }

    fun onScannerMessage(event: ChatScannerEvent) {
        if (!event.loaded) return
        SwingUtilities.invokeLater { addScannerMessage(event.entry, event.message) }
    }

    @Deprecated("switch to event")
    override fun onScannerMessage(entry: ChatScannerEntry, message: PlayerMessage, loaded: Boolean) {
        if (!loaded) return
        SwingUtilities.invokeLater { addScannerMessage(entry, message) }
    }

//    @Deprecated("switch to event")
//    override fun onJoinedArea(playerName: String) {
//        SwingUtilities.invokeLater {
//            for (comp in this.messageComponents!!) {
//                var panelPlayerName: String? = null
//                if (comp is TradeMessagePanel) {
//                    panelPlayerName = comp.tradeOffer.playerName
//                } else if (comp is ChatScannerMessagePanel) {
//                    panelPlayerName = comp.playerMessage.player
//                }
//                if (panelPlayerName == null) continue
//                if (panelPlayerName == playerName) (comp as NotificationPanel).markPlayerJoined()
//            }
//        }
//    }

    fun onJoinedArea(event: PlayerJoinedAreaEvent) {
        if (!event.loaded) return
        SwingUtilities.invokeLater {
            for (comp in this.messageComponents!!) {
                var panelPlayerName: String? = null
                if (comp is TradeMessagePanel) {
                    panelPlayerName = comp.tradeOffer.playerName
                } else if (comp is ChatScannerMessagePanel) {
                    panelPlayerName = comp.playerMessage.player
                }
                if (panelPlayerName == null) continue
                if (panelPlayerName == event.playerName) (comp as NotificationPanel).markPlayerJoined()
            }
        }
    }

    override fun onFontChanged() {
        refresh()
    }

    override fun onSave() {
        setDisplayMode(SaveManager.settingsSaveFile.data.useMessageTabs)
    }

    override fun onJoinedArea(playerName: String) {
        
    }

    companion object {
        private const val MESSAGE_GAP = 1

        // Tabs
        private const val DEFAULT_KEY = "DEFAULT"
        private const val TAB_KEY = "TABS"

        private const val OPACITY_STEP = 0.02f

        // Thread for handling opacity
        private val executor: Executor = Executors.newSingleThreadExecutor()
    }
}
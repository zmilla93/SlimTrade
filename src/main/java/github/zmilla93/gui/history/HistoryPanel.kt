package github.zmilla93.gui.history

import github.zmilla93.App
import github.zmilla93.core.chatparser.ParserLoadedListener
import github.zmilla93.core.chatparser.ParserRestartListener
import github.zmilla93.core.chatparser.TradeListener
import github.zmilla93.core.data.SaleItem
import github.zmilla93.core.data.SaleItemWrapper
import github.zmilla93.core.enums.HistoryOrder
import github.zmilla93.core.event.ParserEventType
import github.zmilla93.core.event.TradeEvent
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.Game
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.core.trading.TradeOfferType
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.saving.ISaveListener
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.table.DefaultTableCellRenderer

/**
 * Handles rendering for a specific category of trades (ie PoE1 incoming).
 */
class HistoryPanel(val game: Game, tradeOfferType: TradeOfferType) : JPanel(), ISaveListener, TradeListener,
    ParserRestartListener,
    ParserLoadedListener {
    private val data = ArrayList<HistoryRowData?>()
    private val table: HistoryTable
    private val tradeOfferType: TradeOfferType

    init {
        assert(tradeOfferType == TradeOfferType.INCOMING_TRADE || tradeOfferType == TradeOfferType.OUTGOING_TRADE)
        this.tradeOfferType = tradeOfferType
        val columnNames = arrayOf("Date", "Time", "Player", "Item", "Price")
        // Table
        // FIXME: Move renderer to a separate class?
        val defaultCellRenderer = DefaultTableCellRenderer()
        defaultCellRenderer.setHorizontalAlignment(SwingConstants.CENTER)
        table = HistoryTable(columnNames, data)
        table.setDefaultRenderer(DateString::class.java, defaultCellRenderer)
        table.setDefaultRenderer(TimeString::class.java, defaultCellRenderer)
        table.setDefaultRenderer(PlayerNameWrapper::class.java, PlayerNameCellRenderer())
        table.setDefaultRenderer(SaleItem::class.java, SaleItemCellRenderer())
        table.setDefaultRenderer(SaleItemWrapper::class.java, SaleItemCellRenderer())
        table.setAutoCreateRowSorter(true)

        // Panel Layout
        setLayout(BorderLayout())
        val scrollPane = JScrollPane(table)
        add(scrollPane, BorderLayout.CENTER)

        // FIXME : Is this the best way to reload table?
        SaveManager.settingsSaveFile.addListener(this)
        App.events.subscribe(TradeEvent::class.java) { onTrade(it) }
        App.events.subscribe(ParserEventType::class.java) {
            if (it == ParserEventType.RESTART) clearAllRows()
            else if (it == ParserEventType.LOADED) reloadUI()
        }
    }

    fun reloadUI() {
        table.historyTableModel.fireTableDataChanged()
    }

    fun addRow(tradeOffer: TradeOffer, updateUI: Boolean) {
        if (data.size >= MAX_MESSAGE_COUNT) data.removeAt(0)
        val rowData = HistoryRowData(tradeOffer)
        data.add(rowData)
        if (updateUI) table.historyTableModel.fireTableDataChanged()
    }

    fun clearAllRows() {
        data.clear()
        table.historyTableModel.fireTableDataChanged()
    }

    fun refreshSelectedTrade() {
        var index = table.selectedRow
        if (index == -1) return
        if (SaveManager.settingsSaveFile.data.historyOrder == HistoryOrder.NEWEST_FIRST) index = data.size - 1 - index
        if (index >= data.size) return
        val trade = data.get(index)!!.tradeOffer
        FrameManager.messageManager.addMessage(trade, false, true)
    }

    override fun onSave() {
        table.historyTableModel.fireTableDataChanged()
    }

    override fun handleTrade(tradeOffer: TradeOffer, loaded: Boolean) {
        assert(SwingUtilities.isEventDispatchThread())
        if (tradeOffer.offerType != tradeOfferType) return
        addRow(tradeOffer, loaded)
    }

    fun onTrade(tradeEvent: TradeEvent) {
        assert(SwingUtilities.isEventDispatchThread())
        val tradeOffer = tradeEvent.tradeOffer
        if (tradeOffer.offerType != tradeOfferType) return
        if (tradeOffer.game != game) return
        addRow(tradeOffer, tradeEvent.isLoaded)
    }

    // FIXME : Remove
    override fun onParserRestart() {
        clearAllRows()
    }

    // FIXME : Remove
    override fun onParserLoaded(dnd: Boolean) {
        reloadUI()
    }

    companion object {
        var MAX_MESSAGE_COUNT: Int = 50
    }
}

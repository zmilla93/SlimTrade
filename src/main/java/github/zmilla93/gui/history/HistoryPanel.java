package github.zmilla93.gui.history;

import github.zmilla93.core.chatparser.IParserInitListener;
import github.zmilla93.core.chatparser.IParserLoadedListener;
import github.zmilla93.core.chatparser.ITradeListener;
import github.zmilla93.core.data.SaleItem;
import github.zmilla93.core.data.SaleItemWrapper;
import github.zmilla93.core.enums.HistoryOrder;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.saving.ISaveListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * Handles rendering for a specific category of trades (ie PoE1 incoming).
 */
public class HistoryPanel extends JPanel implements ISaveListener, ITradeListener, IParserInitListener, IParserLoadedListener {

    public static int MAX_MESSAGE_COUNT = 50;

    private final ArrayList<HistoryRowData> data = new ArrayList<>();
    private final HistoryTable table;
    private final TradeOfferType tradeOfferType;

    public HistoryPanel(TradeOfferType tradeOfferType) {
        assert tradeOfferType == TradeOfferType.INCOMING_TRADE || tradeOfferType == TradeOfferType.OUTGOING_TRADE;
        this.tradeOfferType = tradeOfferType;
        String[] columnNames = new String[]{"Date", "Time", "Player", "Item", "Price"};
        // Table
        // FIXME: Move renderer to a separate class?
        DefaultTableCellRenderer defaultCellRenderer = new DefaultTableCellRenderer();
        defaultCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table = new HistoryTable(columnNames, data);
        table.setDefaultRenderer(DateString.class, defaultCellRenderer);
        table.setDefaultRenderer(TimeString.class, defaultCellRenderer);
        table.setDefaultRenderer(PlayerNameWrapper.class, new PlayerNameCellRenderer());
        table.setDefaultRenderer(SaleItem.class, new SaleItemCellRenderer());
        table.setDefaultRenderer(SaleItemWrapper.class, new SaleItemCellRenderer());
        table.setAutoCreateRowSorter(true);

        // Panel Layout
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // FIXME : Is this the best way to reload table?
        SaveManager.settingsSaveFile.addListener(this);
    }


    public void reloadUI() {
        table.getHistoryTableModel().fireTableDataChanged();
    }

    public void addRow(TradeOffer tradeOffer, boolean updateUI) {
        if (data.size() >= MAX_MESSAGE_COUNT) data.remove(0);
        HistoryRowData rowData = new HistoryRowData(tradeOffer);
        data.add(rowData);
        if (updateUI) table.getHistoryTableModel().fireTableDataChanged();
    }

    public void clearAllRows() {
        data.clear();
        table.getHistoryTableModel().fireTableDataChanged();
    }

    public void refreshSelectedTrade() {
        int index = table.getSelectedRow();
        if (index == -1) return;
        if (SaveManager.settingsSaveFile.data.historyOrder == HistoryOrder.NEWEST_FIRST)
            index = data.size() - 1 - index;
        if (index >= data.size()) return;
        TradeOffer trade = data.get(index).tradeOffer;
        FrameManager.messageManager.addMessage(trade, false, true);
    }

    @Override
    public void onSave() {
        table.getHistoryTableModel().fireTableDataChanged();
    }

    @Override
    public void handleTrade(TradeOffer tradeOffer, boolean loaded) {
        if (tradeOffer.offerType != tradeOfferType) return;
        ZUtil.invokeLater(() -> addRow(tradeOffer, loaded));
    }

    @Override
    public void onParserInit() {
        clearAllRows();
    }

    @Override
    public void onParserLoaded(boolean dnd) {
        reloadUI();
    }

}

package github.zmilla93.gui.history;

import github.zmilla93.core.data.SaleItem;
import github.zmilla93.core.data.SaleItemWrapper;
import github.zmilla93.core.enums.HistoryOrder;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.saving.ISaveListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel implements ISaveListener {

    public static int MAX_MESSAGE_COUNT = 50;

    private final ArrayList<HistoryRowData> data = new ArrayList<>();
    private final HistoryTable table;
    private final JButton reloadButton = new JButton("Open Selected Message");

    public HistoryPanel() {
        String[] columnNames = new String[]{"Date", "Time", "Player", "Item", "Price"};

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        int inset = 2;
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(inset, 0, inset, inset);
        buttonPanel.add(reloadButton, gc);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Table
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
        add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        addListeners();
        SaveManager.settingsSaveFile.addListener(this);
    }

    private void addListeners() {
        reloadButton.addActionListener(e -> refreshSelectedTrade());
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

    private void refreshSelectedTrade() {
        int index = table.getSelectedRow();
        if (SaveManager.settingsSaveFile.data.historyOrder == HistoryOrder.NEWEST_FIRST)
            index = data.size() - 1 - index;
        if (index == -1 || index >= data.size()) return;
        TradeOffer trade = data.get(index).tradeOffer;
        FrameManager.messageManager.addMessage(trade, false, true);
    }

    @Override
    public void onSave() {
        table.getHistoryTableModel().fireTableDataChanged();
    }

}

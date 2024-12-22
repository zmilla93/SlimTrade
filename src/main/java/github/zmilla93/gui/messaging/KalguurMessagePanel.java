package github.zmilla93.gui.messaging;

import github.zmilla93.core.enums.ButtonRow;
import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.core.utility.POEInterface;

public class KalguurMessagePanel extends NotificationPanel {

    public KalguurMessagePanel() {
        super(ThemeColor.SCANNER_MESSAGE, null, ButtonRow.TOP_ROW);
        topContainer.setVisible(false);
        itemButton.setText("Shipment Complete!");
        itemButton.addActionListener(e -> POEInterface.pasteWithFocus("/kingsmarch"));
        setup();
    }

}

package github.zmilla93.gui.messaging;

import github.zmilla93.core.enums.ButtonRow;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.modules.theme.OLD_ThemeColor;

public class KalguurMessagePanel extends NotificationPanel {

    public KalguurMessagePanel() {
        super(OLD_ThemeColor.SCANNER_MESSAGE, null, ButtonRow.TOP_ROW);
        topContainer.setVisible(false);
        itemButton.setText("Shipment Complete!");
        itemButton.addActionListener(e -> POEInterface.pasteWithFocus("/kingsmarch"));
        setup();
    }

}

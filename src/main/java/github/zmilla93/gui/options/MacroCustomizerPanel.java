package github.zmilla93.gui.options;

import github.zmilla93.core.enums.ButtonRow;
import github.zmilla93.core.enums.CustomIcon;
import github.zmilla93.core.enums.MacroButtonType;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.*;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MacroCustomizerPanel extends AddRemovePanel<MacroButton> {

    // Macro Button
    private final JComboBox<ImageIcon> iconCombo = new LimitCombo<>();
    private final JTextField buttonText = new PlaceholderTextField("Text...");

    private final int TEXT_FIELD_COLUMNS = 20;
    private final JTextField lmbInput = new JTextField(TEXT_FIELD_COLUMNS);
    private final JTextField rmbInput = new JTextField(TEXT_FIELD_COLUMNS);
    private final JCheckBox closeCheckbox = new JCheckBox("Close");
    private final JComboBox<ButtonRow> rowCombo = new JComboBox<>();
    private final JComboBox<MacroButtonType> buttonType = new JComboBox<>();
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    // Internal
    private final CardLayout displayLayout = new CardLayout();
    private final JPanel displayPanel = new JPanel(displayLayout);

    public MacroCustomizerPanel() {
        setLayout(new GridBagLayout());

        // Labels
        JLabel lmbLabel = new JLabel("LMB");
        JLabel rmbLabel = new JLabel("RMB");
        ThemeManager.addStickyCombo(iconCombo);
        displayPanel.add(iconCombo, MacroButtonType.ICON.toString());
        displayPanel.add(buttonText, MacroButtonType.TEXT.toString());

        // Add items to combos
        for (ButtonRow row : ButtonRow.values()) rowCombo.addItem(row);
        for (CustomIcon icon : CustomIcon.values()) iconCombo.addItem(ThemeManager.getColorIcon(icon.path()));
        for (MacroButtonType type : MacroButtonType.values()) buttonType.addItem(type);

        // Delete & Shift Buttons
        GridBagConstraints gc = ZUtil.getGC();
        add(deleteButton, gc);
        gc.gridy++;
        add(super.dragButton, gc);
        gc.gridy = 0;
        gc.gridx++;
        gc.insets = new Insets(0, 0, 0, 2);
        gc.insets.right = 0;

        // Mouse Labels
        add(lmbLabel, gc);
        gc.gridy++;
        add(rmbLabel, gc);
        gc.gridx++;
        gc.gridy = 0;

        // Response Input
        add(lmbInput, gc);
        gc.gridy++;
        add(rmbInput, gc);
        gc.gridx++;
        gc.gridy = 0;

        // Button type & icon/text
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        add(buttonType, gc);
        gc.gridy++;
        add(displayPanel, gc);
        gc.gridx++;
        gc.gridy = 0;

        // Row & Hotkey
        add(rowCombo, gc);
        gc.gridy++;
        add(new ButtonWrapper(hotkeyButton), gc);
        gc.gridx++;
        gc.gridy = 0;
        add(closeCheckbox, gc);
        gc.gridx++;

        updateUI();
        addListeners();
    }

    private void addListeners() {
        deleteButton.addActionListener(e -> {
            // FIXME : Handle sticky combo purging better
            ThemeManager.removeStickyCombo(iconCombo);
        });
        buttonType.addActionListener(e -> {
            if (buttonType.getSelectedItem() == null) return;
            displayLayout.show(displayPanel, Objects.requireNonNull(buttonType.getSelectedItem()).toString());
        });
    }

    @Override
    public MacroButton getData() {
        MacroButton macro = new MacroButton();
        macro.lmbResponse = lmbInput.getText();
        macro.rmbResponse = rmbInput.getText();
        macro.row = (ButtonRow) rowCombo.getSelectedItem();
        macro.buttonType = (MacroButtonType) buttonType.getSelectedItem();
        macro.text = buttonText.getText();
        macro.hotkeyData = hotkeyButton.getData();
        macro.close = closeCheckbox.isSelected();
        int index = iconCombo.getSelectedIndex();
        if (index == -1) index = 0;
        macro.icon = CustomIcon.values()[index];
        return macro;
    }

    @Override
    public void setData(MacroButton macro) {
        lmbInput.setText(macro.lmbResponse);
        rmbInput.setText(macro.rmbResponse);
        rowCombo.setSelectedItem(macro.row);
        buttonType.setSelectedItem(macro.buttonType);
        buttonText.setText(macro.text);
        iconCombo.setSelectedIndex(macro.icon.ordinal());
        hotkeyButton.setData(macro.hotkeyData);
        closeCheckbox.setSelected(macro.close);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (iconCombo != null) {
            int selectedIndex = iconCombo.getSelectedIndex();
            iconCombo.removeAllItems();
            for (CustomIcon icon : CustomIcon.values()) {
                iconCombo.addItem(ThemeManager.getColorIcon(icon.path()));
            }
            iconCombo.setSelectedIndex(selectedIndex);
        }
        setBorder(UIManager.getBorder("TextField.border"));
    }

}

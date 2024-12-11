package github.zmilla93.gui.options.general;

import github.zmilla93.core.enums.SliderRange;
import github.zmilla93.core.enums.SpinnerRange;
import github.zmilla93.core.enums.SpinnerRangeFloat;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ButtonWrapper;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.RangeSlider;
import github.zmilla93.gui.components.RangeSpinner;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.options.HotkeyOptionPanel;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class MessageSettingsPanel extends JPanel implements ISavable {

    // Fade
    private final JCheckBox fadeCheckBox = new JCheckBox("Fade Messages");
    private final JSpinner secondsBeforeFadeSpinner = new RangeSpinner(SpinnerRangeFloat.SECONDS_BEFORE_FADE);
    private final JLabel fadedOpacityLabel = new JLabel();
    private final JSlider fadeOpacitySlider = new RangeSlider(SliderRange.FADED_OPACITY);

    // Tabs
    private final JCheckBox messageTabsCheckbox = new JCheckBox("Use Tabs");
    private final HotkeyButton previousMessageTabHotkey = new HotkeyButton();
    private final HotkeyButton nextMessageTabHotkey = new HotkeyButton();

    // Collapse
    private final JCheckBox collapseCheckBox = new JCheckBox("Collapse Messages");
    private final JSpinner messagesBeforeCollapseSpinner = new RangeSpinner(SpinnerRange.MESSAGES_BEFORE_COLLAPSE);

    // Panels
    private final JPanel fadePanel;
    //    private final JPanel messageTabHotkeyPanel;
    private final JPanel collapsePanel;
    private static final int INSET = 20;

    public MessageSettingsPanel() {

//        messageTabHotkeyPanel = createTabMessageHotkeyPanel();
        fadePanel = createFadePanel();
        fadeOpacitySlider.setPreferredSize(new Dimension(Math.round(getPreferredSize().width * 0.75f), fadeOpacitySlider.getPreferredSize().height));
        collapsePanel = createCollapsePanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(fadeCheckBox, gc);
        gc.gridy++;
        add(fadePanel, gc);
        gc.gridy++;
        add(messageTabsCheckbox, gc);
        gc.gridy++;
//        add(messageTabHotkeyPanel, gc);
//        gc.gridy++;
        add(collapseCheckBox, gc);
        gc.gridy++;
        add(collapsePanel, gc);
        gc.gridy++;
        addListeners();
    }

    private void addListeners() {
        fadeOpacitySlider.addChangeListener(e -> updateOpacityLabel());
        fadeCheckBox.addActionListener(e -> updatePanelVisibility());
        messageTabsCheckbox.addChangeListener(e -> updatePanelVisibility());
        collapseCheckBox.addActionListener(e -> updatePanelVisibility());
    }

    // FIXME: Unused, can delete if satisfied with having hotkeys on the hotkey panel instead (and delete other commented out code that is related)
    private JPanel createTabMessageHotkeyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Previous Message Tab"), gc);
        gc.gridx++;
        gc.insets.left = HotkeyOptionPanel.SPACING_INSET;
        panel.add(new ButtonWrapper(previousMessageTabHotkey), gc);
        gc.gridx = 0;
        gc.gridy++;
        gc.insets.left = 0;
        panel.add(new JLabel("Next Message Tab"), gc);
        gc.gridx++;
        gc.insets.left = HotkeyOptionPanel.SPACING_INSET;
        panel.add(new ButtonWrapper(nextMessageTabHotkey), gc);
        return panel;
    }

    private JPanel createCollapsePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Messages Before Collapse"), gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        gc.insets.left = INSET;
        panel.add(messagesBeforeCollapseSpinner, gc);
        gc.fill = GridBagConstraints.HORIZONTAL;
        return panel;
    }

    private JPanel createFadePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Seconds Before Fading"), gc);
        gc.gridx++;
        gc.fill = GridBagConstraints.NONE;
        gc.insets.left = INSET;
        panel.add(secondsBeforeFadeSpinner, gc);
        gc.insets.left = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy++;
        panel.add(fadedOpacityLabel, gc);
        gc.gridx++;
        panel.add(fadeOpacitySlider, gc);
        return panel;
    }

    private void updatePanelVisibility() {
        fadePanel.setVisible(fadeCheckBox.isSelected());
        FrameManager.optionsWindow.getHotkeyPanel().showHideChangeTabHotkeys(messageTabsCheckbox.isSelected());
        collapseCheckBox.setVisible(!messageTabsCheckbox.isSelected());
        collapsePanel.setVisible(collapseCheckBox.isSelected() && !messageTabsCheckbox.isSelected());
    }

    private void updateOpacityLabel() {
        fadedOpacityLabel.setText("Faded Opacity (" + fadeOpacitySlider.getValue() + "%)");
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.fadeMessages = fadeCheckBox.isSelected();
        SaveManager.settingsSaveFile.data.secondsBeforeFading = (float) secondsBeforeFadeSpinner.getValue();
        SaveManager.settingsSaveFile.data.fadedOpacity = fadeOpacitySlider.getValue();
        SaveManager.settingsSaveFile.data.useMessageTabs = messageTabsCheckbox.isSelected();
        SaveManager.settingsSaveFile.data.collapseMessages = collapseCheckBox.isSelected();
        SaveManager.settingsSaveFile.data.messageCountBeforeCollapse = (int) messagesBeforeCollapseSpinner.getValue();
    }

    @Override
    public void load() {
        fadeCheckBox.setSelected(SaveManager.settingsSaveFile.data.fadeMessages);
        secondsBeforeFadeSpinner.setValue(SaveManager.settingsSaveFile.data.secondsBeforeFading);
        fadeOpacitySlider.setValue(SaveManager.settingsSaveFile.data.fadedOpacity);
        messageTabsCheckbox.setSelected(SaveManager.settingsSaveFile.data.useMessageTabs);
        collapseCheckBox.setSelected(SaveManager.settingsSaveFile.data.collapseMessages);
        messagesBeforeCollapseSpinner.setValue(SaveManager.settingsSaveFile.data.messageCountBeforeCollapse);
        updatePanelVisibility();
        updateOpacityLabel();
    }

}

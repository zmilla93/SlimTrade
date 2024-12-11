package github.zmilla93.gui.options.stash;

import github.zmilla93.core.data.StashTabData;
import github.zmilla93.core.enums.MatchType;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.AddRemovePanel;
import github.zmilla93.gui.components.PlaceholderTextField;
import github.zmilla93.modules.theme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;

public class StashRow extends AddRemovePanel<StashTabData> {

    private final JTextField nameInput = new PlaceholderTextField("Stash Tab Name...", 20);
    private final JComboBox<MatchType> matchTypeCombo = new JComboBox<>();
    private final JComboBox<StashTabType> stashTypeCombo = new JComboBox<>();
    private final JComboBox<Color> colorCombo = new ColorCombo();

    public StashRow() {
        setLayout(new GridBagLayout());
        for (MatchType matchType : MatchType.values()) matchTypeCombo.addItem(matchType);
        for (StashTabType stashType : StashTabType.values()) stashTypeCombo.addItem(stashType);

        GridBagConstraints gc = ZUtil.getGC();
        add(deleteButton, gc);
        gc.gridx++;
        add(dragButton, gc);
        gc.gridx++;
        add(nameInput, gc);
        gc.gridx++;
        add(matchTypeCombo, gc);
        gc.gridx++;
        add(stashTypeCombo, gc);
        gc.gridx++;
        add(colorCombo, gc);
        gc.gridx++;
    }

    @Override
    public StashTabData getData() {
        return new StashTabData(
                nameInput.getText(),
                (MatchType) matchTypeCombo.getSelectedItem(),
                (StashTabType) stashTypeCombo.getSelectedItem(),
                colorCombo.getSelectedIndex());
    }

    @Override
    public void setData(StashTabData data) {
        nameInput.setText(data.stashTabName);
        matchTypeCombo.setSelectedItem(data.matchType);
        stashTypeCombo.setSelectedItem(data.stashTabType);
        colorCombo.setSelectedIndex(data.stashColorIndex);
    }

}

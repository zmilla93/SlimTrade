package github.zmilla93.gui.components;

import javax.swing.*;

public class StyledLabelBuilder {

    private final JPanel panel = new ComponentPanel(0);
    private StyledLabel mostRecentLabel;

    private StyledLabelBuilder() {
        // Static Builder Class
    }

    public static StyledLabelBuilder builder(String text) {
        StyledLabelBuilder builder = new StyledLabelBuilder();
        return builder.label(text);
    }

    public StyledLabelBuilder label(String text) {
        mostRecentLabel = new StyledLabel(text);
        panel.add(mostRecentLabel);
        return this;
    }

    public StyledLabelBuilder bold() {
        mostRecentLabel.bold();
        return this;
    }

    public StyledLabelBuilder italic() {
        mostRecentLabel.italic();
        return this;
    }

    public JPanel build() {
        return panel;
    }

}

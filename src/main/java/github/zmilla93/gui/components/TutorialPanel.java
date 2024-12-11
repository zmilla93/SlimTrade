package github.zmilla93.gui.components;

import github.zmilla93.App;
import github.zmilla93.core.utility.GUIReferences;
import github.zmilla93.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class TutorialPanel extends JPanel {

    private final GridBagConstraints gc = ZUtil.getGC();
    private final JPanel contentPanel = new JPanel(new GridBagLayout());

    public TutorialPanel() {
//        setLayout(new GridBagLayout());
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.NORTH);
        gc.weightx = 1;
//        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        if (App.debugUIBorders >= 1) setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    public void addHeader(String title) {
        JLabel label = new StyledLabel(title).bold();
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);

        // Title Panel
        JPanel titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titleGC = ZUtil.getGC();
        titlePanel.add(label, titleGC);

        // Outer panel
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(titlePanel, BorderLayout.WEST);
        outerPanel.add(separator, BorderLayout.SOUTH);
        if (App.debugUIBorders >= 1) outerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        gc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(outerPanel, gc);
        gc.gridy++;
        addVerticalStrutSmall();
    }

    public StyledLabel addLabel(String text) {
        StyledLabel label = new StyledLabel(text);
        addComponent(label);
        return label;
    }

    public Component addComponent(Component component) {
        return addComponent(component, false);
    }

    public Component addComponent(Component component, boolean center) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints panelGC = ZUtil.getGC();
        panel.add(component, panelGC);

        if (center) gc.fill = GridBagConstraints.HORIZONTAL;
        else gc.fill = GridBagConstraints.NONE;
        contentPanel.add(panel, gc);
        gc.gridy++;
        return component;
    }

    public void addVerticalStrut() {
        contentPanel.add(Box.createVerticalStrut(GUIReferences.INSET), gc);
        gc.gridy++;
    }

    public void addVerticalStrutSmall() {
        contentPanel.add(Box.createVerticalStrut(GUIReferences.SMALL_INSET), gc);
        gc.gridy++;
    }

}

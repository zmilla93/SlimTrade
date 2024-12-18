package github.zmilla93.gui.options;

import github.zmilla93.App;
import github.zmilla93.core.utility.GUIReferences;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.CustomScrollPane;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.modules.updater.ZLogger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Used to create consistently formatted option panels.
 * Do not add components directly. Instead, use the addHeader() and addComponent() functions.
 */
// FIXME : This is no longer abstract, should rename
public class AbstractOptionPanel extends JPanel {

    protected JPanel contentPanel;
    private final GridBagConstraints gc = new GridBagConstraints();
    private static final int CONTENT_PANEL_INSET = 10;

    public AbstractOptionPanel() {
        this(true, true);
    }

    public AbstractOptionPanel(boolean addScrollPanel) {
        this(addScrollPanel, true);
    }

    public AbstractOptionPanel(boolean addScrollPanel, boolean addInsets) {
        setLayout(new BorderLayout());
        contentPanel = new JPanel(new GridBagLayout());
        JPanel insetPanel = new JPanel(new BorderLayout());
        if (App.debugUIBorders >= 1) {
            setBackground(new Color(96, 236, 122));
            contentPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        }
        // FIXME: if insets are disable, should not add insets panel at all
        if (addInsets) {
            insetPanel.add(Box.createVerticalStrut(CONTENT_PANEL_INSET), BorderLayout.NORTH);
            insetPanel.add(Box.createVerticalStrut(CONTENT_PANEL_INSET), BorderLayout.SOUTH);
            insetPanel.add(Box.createHorizontalStrut(CONTENT_PANEL_INSET), BorderLayout.EAST);
            insetPanel.add(Box.createHorizontalStrut(CONTENT_PANEL_INSET), BorderLayout.WEST);
        }
        insetPanel.add(contentPanel, BorderLayout.CENTER);
        JPanel bufferPanel = new JPanel(new BorderLayout());
        bufferPanel.add(insetPanel, BorderLayout.NORTH);
        if (addScrollPanel) {
            JScrollPane scrollPane = new CustomScrollPane(bufferPanel);
            super.add(scrollPane, BorderLayout.CENTER);
        } else {
            super.add(bufferPanel, BorderLayout.CENTER);
        }
        gc.gridx = 0;
        gc.gridy = 0;
    }

    public HeaderPanel addHeader(String title) {
        HeaderPanel headerPanel = new HeaderPanel(title);
        int prevFill = gc.fill;
        double prevWeightX = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        contentPanel.add(headerPanel, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeightX;
        gc.gridy++;
        contentPanel.add(Box.createVerticalStrut(2), gc);
        gc.gridy++;
        return headerPanel;
    }

    public JLabel addLabel(String text) {
        JLabel label = new JLabel(text);
        addComponent(label);
        return label;
    }

    public StyledLabel addStyledLabel(String text) {
        StyledLabel label = new StyledLabel(text);
        addComponent(label);
        return label;
    }

    // FIXME: Refactor to add()?
    public Component addComponent(Component component) {
        if (App.debugUIBorders >= 1) {
            if (component instanceof JPanel)
                ((JPanel) component).setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            else if (App.debugUIBorders >= 2 && component instanceof JComponent) {
                JPanel debugPanel = new JPanel(new BorderLayout());
                debugPanel.add(component);
                debugPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
                debugPanel.add(component, BorderLayout.CENTER);
                component = debugPanel;
            }
        }
        int prevFill = gc.fill;
        double prevWeight = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        JPanel outerPanel = new JPanel(new BorderLayout());
        JPanel innerPanel = new JPanel(new BorderLayout());
        // FIXME : Add inset (5) when done debugging.
        outerPanel.add(Box.createHorizontalStrut(0), BorderLayout.WEST);
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        innerPanel.add(component, BorderLayout.WEST);
        contentPanel.add(outerPanel, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeight;
        gc.gridy++;
        return outerPanel;
    }

    public Component addFullWidthComponent(Component component) {
        int prevFill = gc.fill;
        double prevWeightX = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        contentPanel.add(component, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeightX;
        gc.gridy++;
        return component;
    }

    public Component addVerticalStrut() {
        Component strut = Box.createVerticalStrut(GUIReferences.INSET);
        addComponent(strut);
        return strut;
    }

    public Component addVerticalStrutSmall() {
        Component strut = Box.createVerticalStrut(GUIReferences.SMALL_INSET);
        addComponent(strut);
        return strut;
    }

    // Components should only be added using provided functions.
    // Override all add functions to warn about improper use.

    @Override
    public Component add(Component comp) {
        return addComponent(comp);
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        incorrectAddMethod();
        super.add(comp, constraints);
    }

    @Override
    public Component add(String name, Component comp) {
        incorrectAddMethod();
        return super.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        incorrectAddMethod();
        return super.add(comp, index);
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        incorrectAddMethod();
        super.add(comp, constraints, index);
    }

    private void incorrectAddMethod() {
        ZLogger.err("[OptionPanel] Components should not be added directly to an OptionPanel! Use addHeader() or addComponent() instead.");
        ZUtil.printCallingFunction(AbstractOptionPanel.class);
    }

}

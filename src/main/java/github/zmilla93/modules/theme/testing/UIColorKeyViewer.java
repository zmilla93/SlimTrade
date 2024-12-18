package github.zmilla93.modules.theme.testing;

import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.PlaceholderTextField;
import github.zmilla93.modules.theme.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * Displays color keys using every available theme
 */
public class UIColorKeyViewer extends JFrame {

    public static final String[] startingKeys = {"Actions.Green", "Actions.Red"};
    private final JTextField inputField = new PlaceholderTextField("Add Color Key...", 20);
    private final GridBagConstraints gc = ZUtil.getGC();
    private final JPanel containerPanel = new JPanel(new GridBagLayout());
    private final int startingColumnCount = startingKeys.length + 1;

    public UIColorKeyViewer() {
        setTitle("UI Color Key Viewer");
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(initColorPanel());
        add(inputField, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        pack();
        POEWindow.windowToCorner(this);
        setVisible(true);
        inputField.addActionListener(e -> {
            tryAddNewColorKey(inputField.getText().trim());
            inputField.setText("");
        });
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
    }

    private JPanel initColorPanel() {
        gc.fill = GridBagConstraints.BOTH;
        LookAndFeel currentLAF = UIManager.getLookAndFeel();
        for (Theme theme : Theme.values()) {
            try {
                UIManager.setLookAndFeel(theme.lookAndFeel);
            } catch (UnsupportedLookAndFeelException e) {
                continue;
            }
            JLabel themeNameLabel = new JLabel(theme.toString());
            containerPanel.add(wrapperPanel(themeNameLabel), gc);
            gc.gridx++;
            for (String key : startingKeys) {
                JLabel label = new JLabel(key);
                label.setForeground(UIManager.getColor(key));
                containerPanel.add(wrapperPanel(label), gc);
                gc.gridx++;
            }
            gc.gridx = 0;
            gc.gridy++;
        }
        gc.gridx = startingKeys.length + 1;
        gc.gridy = 0;
        try {
            UIManager.setLookAndFeel(currentLAF);
        } catch (UnsupportedLookAndFeelException ignore) {
        }
        return containerPanel;
    }

    /// Adds a new color to the display if given a valid color key.
    private void tryAddNewColorKey(String colorKey) {
        LookAndFeel currentLAF = UIManager.getLookAndFeel();
        for (Theme theme : Theme.values()) {
            try {
                UIManager.setLookAndFeel(theme.lookAndFeel);
            } catch (UnsupportedLookAndFeelException e) {
                continue;
            }
            Object value = UIManager.get(colorKey);
            if (!(value instanceof Color)) return;
            Color color = (Color) value;
            JLabel label = new JLabel(colorKey);
            label.setForeground(color);
            containerPanel.add(wrapperPanel(label), gc);
            gc.gridy++;
        }
        try {
            UIManager.setLookAndFeel(currentLAF);
        } catch (UnsupportedLookAndFeelException ignore) {
        }
        gc.gridy = 0;
        gc.gridx++;
        pack();
    }

    private JPanel wrapperPanel(Component component) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        int insetX = 10;
        int insetY = 4;
        gc.insets = new Insets(insetY, insetX, insetY, insetX);
        panel.add(component, gc);
        return panel;
    }

}

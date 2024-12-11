package github.zmilla93.modules.theme.testing;

import github.zmilla93.core.utility.DocumentChangeAdapter;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.CustomScrollPane;
import github.zmilla93.gui.components.PlaceholderTextField;
import github.zmilla93.modules.theme.IThemeListener;
import github.zmilla93.modules.theme.Theme;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.components.ThemeFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * A window for inspecting every unique color in the current color theme.
 */
public class UIManagerInspectorWindow extends ThemeFrame implements IThemeListener {

    private final HashMap<Color, ArrayList<String>> colorMap = new HashMap<>();
    private final JPanel containerPanel = new JPanel();
    boolean dirty = false;
    private final JCheckBox sharedKeysCheckbox = new JCheckBox("Only Show Shared Keys");
    private Set<String> sharedKeys = new HashSet<>();
    private final JTextField searchTextField = new PlaceholderTextField("Search...", 20);
    private final JLabel colorCountLabel = new JLabel();
    private int visibleComponentCount = 0;

    public UIManagerInspectorWindow() {
        setTitle("UIManager Inspector");
        buildSharedKeysList();
        sharedKeysCheckbox.setSelected(true);
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        northPanel.add(new ComponentPanel(sharedKeysCheckbox, searchTextField, colorCountLabel), BorderLayout.WEST);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(new CustomScrollPane(containerPanel), BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        buildColorPanel();
        pack();
        setSize(800, 900);
        ThemeManager.addThemeListener(this);
        addListeners();
//        refreshVisiblePanels();
//        updateCountLabel();
    }

    private void addListeners() {
        sharedKeysCheckbox.addActionListener(e -> refreshVisiblePanels());
        searchTextField.getDocument().addDocumentListener(new DocumentChangeAdapter() {
            @Override
            public void onDocumentChange() {
                refreshVisiblePanels();
            }
        });
    }

    private void updateCountLabel() {
        colorCountLabel.setText("Showing " + visibleComponentCount + " colors.");
    }

    private void refreshVisiblePanels() {
        ArrayList<DisplayPanel> panelsToShow = getInitialPanelList();
        String searchTerm = searchTextField.getText().trim();
        if (!searchTerm.isEmpty()) {
            for (int i = panelsToShow.size() - 1; i >= 0; i--) {
                DisplayPanel targetPanel = panelsToShow.get(i);
                boolean success = false;
                for (String key : targetPanel.keys)
                    if (key.toLowerCase().contains(searchTerm)) {
                        success = true;
                        break;
                    }
                if (!success) panelsToShow.remove(i);
            }
        }
        for (DisplayPanel panel : panelsToShow) panel.setVisible(true);
        UIManagerInspectorWindow.this.revalidate();
        UIManagerInspectorWindow.this.repaint();
        if (!panelsToShow.isEmpty()) visibleComponentCount = panelsToShow.size();
        updateCountLabel();
    }

    private @NotNull ArrayList<DisplayPanel> getInitialPanelList() {
        ArrayList<DisplayPanel> panelsToShow = new ArrayList<>();
        for (Component component : containerPanel.getComponents()) {
            DisplayPanel panel = (DisplayPanel) component;
            panel.applyFilter(searchTextField.getText().trim());
            panel.setVisible(false);
            if (sharedKeysCheckbox.isSelected()) {
                for (String key : panel.keys) {
                    if (sharedKeys.contains(key)) {
                        panelsToShow.add(panel);
                        break;
                    }
                }
            } else panelsToShow.add(panel);
        }
        return panelsToShow;
    }

    private void buildColorPanel() {
        containerPanel.removeAll();
        colorMap.clear();
        if (!isVisible()) {
            dirty = true;
            return;
        }
        // Build a map of unique colors, mapped to an array of keys for that color.
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof ColorUIResource) {
                Color color = (Color) value;
                ArrayList<String> localColorKeys;
                if (colorMap.containsKey(color)) localColorKeys = colorMap.get(color);
                else {
                    localColorKeys = new ArrayList<>();
                    colorMap.put(color, localColorKeys);
                }
                localColorKeys.add(key.toString());
            }
        }
        visibleComponentCount = colorMap.size();
        for (Map.Entry<Color, ArrayList<String>> entry : colorMap.entrySet())
            containerPanel.add(new DisplayPanel(entry.getKey(), entry.getValue()));
        refreshVisiblePanels();
    }

    private Set<String> getColorsKeysForCurrentTheme() {
        Set<String> allColorsInTheme = new HashSet<>();
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof ColorUIResource) {
                allColorsInTheme.add(key.toString());
            }
        }
        return allColorsInTheme;
    }

    private void buildSharedKeysList() {
        LookAndFeel currentLAF = UIManager.getLookAndFeel();
        sharedKeys.clear();
        boolean isFirstTheme = true;
        for (Theme theme : Theme.values()) {
            try {
                UIManager.setLookAndFeel(theme.lookAndFeel);
            } catch (UnsupportedLookAndFeelException e) {
                continue;
            }
            if (isFirstTheme) {
                sharedKeys.addAll(getColorsKeysForCurrentTheme());
                isFirstTheme = false;
            } else {
                Set<String> newSharedKeys = new HashSet<>();
                for (String string : getColorsKeysForCurrentTheme())
                    if (sharedKeys.contains(string)) newSharedKeys.add(string);
                sharedKeys = newSharedKeys;
            }
        }
        try {
            UIManager.setLookAndFeel(currentLAF);
        } catch (UnsupportedLookAndFeelException ignore) {
        }
    }

    @Override
    public void onThemeChange() {
        buildColorPanel();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);
        if (dirty) {
            dirty = false;
            buildColorPanel();
        }
    }

    private static class DisplayPanel extends JPanel {

        public final ArrayList<String> keys;
        public final ArrayList<String> filteredKeys = new ArrayList<>();
        private int keyIndex = 0;

        private final JLabel label = new JLabel();
        private final JButton copyKeyButton = new JButton("Copy Key");
        private final JButton copyColorButton = new JButton("Copy Color");
        private final Color color;

        DisplayPanel(Color color, ArrayList<String> keys) {
            this.color = color;
            this.keys = keys;
            Color colorNoAlpha = new Color(color.getRed(), color.getGreen(), color.getBlue());
            setBackground(colorNoAlpha);
            label.setOpaque(true);
            Color backgroundColor = ThemeManager.getCurrentTheme().isDark() ? new Color(0, 0, 0, 180) : new Color(255, 255, 255, 180);
            label.setBackground(backgroundColor);

            setLayout(new GridBagLayout());
            GridBagConstraints gc = ZUtil.getGC();
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.weightx = 0;
            add(copyColorButton, gc);
            gc.gridx++;
            add(copyKeyButton, gc);
            gc.gridx++;
            add(label, gc);
            gc.gridx++;
            gc.weightx = 1;
            add(Box.createHorizontalBox(), gc);

            updateLabel();
            addListeners();
        }

        public void applyFilter(String searchTerm) {
            if (searchTerm == null) {
                filteredKeys.clear();
                filteredKeys.addAll(keys);
                return;
            }
            filteredKeys.clear();
            for (String key : keys) {
                if (key.toLowerCase().contains(searchTerm)) filteredKeys.add(key);
            }
            updateLabel();
        }

        private void addListeners() {
            copyColorButton.addActionListener(e -> ZUtil.setClipboardContents(toColorString(color)));
            copyKeyButton.addActionListener(e -> ZUtil.setClipboardContents(keys.get(keyIndex)));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) cycleKey(true);
                    else if (e.getButton() == MouseEvent.BUTTON3) cycleKey(false);
                    label.getParent().repaint();
                }
            });
        }

        private void updateLabel() {
            if (filteredKeys.isEmpty()) return;
            if (keyIndex > filteredKeys.size() - 1) keyIndex = filteredKeys.size() - 1;
            label.setText(filteredKeys.get(keyIndex) + " " + (keyIndex + 1) + "/" + filteredKeys.size());
        }

        private String toColorString(Color color) {
            return String.format("new Color(%d, %d, %d);", color.getRed(), color.getGreen(), color.getBlue());
        }

        private void cycleKey(boolean forward) {
            if (forward) keyIndex++;
            else keyIndex--;
            if (keyIndex < 0) keyIndex = keys.size() - 1;
            if (keyIndex >= keys.size()) keyIndex = 0;
            updateLabel();
        }

    }

}

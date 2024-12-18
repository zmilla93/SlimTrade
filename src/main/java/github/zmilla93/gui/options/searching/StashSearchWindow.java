package github.zmilla93.gui.options.searching;

import github.zmilla93.core.enums.StashTabColor;
import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.listening.IDefaultSizeAndLocation;
import github.zmilla93.gui.pinning.PinManager;
import github.zmilla93.gui.windows.CustomDialog;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.components.AdvancedButton;
import github.zmilla93.modules.theme.listeners.IFontChangeListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StashSearchWindow extends CustomDialog implements IFontChangeListener, IDefaultSizeAndLocation {

    private boolean combinedWindow = false;
    private StashSearchGroupData data;

    /**
     * Mutual constructor, should be called by all other constructors.
     *
     * @param title Display title
     */
    private StashSearchWindow(String title) {
        super(title, true, false);
        setMinimumSize(null);
        setFocusable(false);
        setFocusableWindowState(false);
        setResizable(false);
        ThemeManager.addFontListener(this);
    }

    /**
     * Constructor when using Separate windows.
     *
     * @param data Search Data
     */
    public StashSearchWindow(StashSearchGroupData data) {
        this(data.title);
        this.data = data;
        JPanel buttonPanel = buildButtonPanel(data);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(buttonPanel, gc);
        pack();
        handlePinSetup();
    }

    /**
     * Constructor when using Combined windows.
     *
     * @param data ArrayList of Search Data
     */
    public StashSearchWindow(ArrayList<StashSearchGroupData> data) {
        this("Searching");
        combinedWindow = true;
        JComboBox<JPanel> comboBox = new JComboBox<>();
        for (StashSearchGroupData group : data) {
            JPanel panel = buildButtonPanel(group);
            comboBox.addItem(panel);
        }
        JPanel buttonContainer = new JPanel();

        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(comboBox, gc);
        gc.gridy++;
        contentPanel.add(buttonContainer, gc);
        buttonContainer.setLayout(new GridBagLayout());
        comboBox.addActionListener(e -> {
            buttonContainer.removeAll();
            JPanel panel = (JPanel) comboBox.getSelectedItem();
            if (panel != null) buttonContainer.add(panel, gc);
            pack();
        });
        JPanel panel = comboBox.getItemAt(0);
        if (panel != null) buttonContainer.add(panel, gc);
        pack();
        handlePinSetup();
    }

    private void handlePinSetup() {
        pinRespectsSize = false;
        if (combinedWindow) PinManager.searchWindow = this;
    }

    private JPanel buildButtonPanel(StashSearchGroupData data) {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            public String toString() {
                return data.title;
            }
        };
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        int INSET_SIZE = 1;
        gc.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
        for (StashSearchTermData term : data.terms) {
            // FIXME : Make sure term title can't be only white space
            if (term.title.equals("")) continue;
            JButton button = createSearchButton(term);
            panel.add(button, gc);
            gc.insets.top = 0;
            gc.gridy++;
        }
        return panel;
    }

    private JButton createSearchButton(StashSearchTermData term) {
        AdvancedButton button = new AdvancedButton(term.title);
        Color outerBorderColor = UIManager.getColor("Button.background");
        Color innerBorderColor = UIManager.getColor("Button.borderColor");
        if (term.colorIndex > 0) {
            StashTabColor stashColor = StashTabColor.get(term.colorIndex);
            button.setBackground(stashColor.getBackground());
            button.setBackgroundHover(ThemeManager.lighter(stashColor.getBackground()));
            button.setForeground(stashColor.getForeground());
            outerBorderColor = stashColor.getBackground();
            innerBorderColor = stashColor.getForeground();
        }
        int singleBorderSize = 1;
        Border outerBorder = BorderFactory.createLineBorder(outerBorderColor, singleBorderSize);
        Border innerBorder = BorderFactory.createLineBorder(innerBorderColor, singleBorderSize);
        button.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        button.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    POEInterface.searchInStash(term.searchTerm);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    POEInterface.searchInStash("");
                }
            }
        });
        return button;
    }

    public StashSearchGroupData getData() {
        return data;
    }

    @Override
    public String getPinTitle() {
        if (combinedWindow) return super.getPinTitle();
        return data.getPinTitle();
    }

    @Override
    public void onFontChanged() {
        pack();
    }

    @Override
    public void dispose() {
        super.dispose();
        ThemeManager.removeFontChangeListener(this);
    }


    @Override
    public void applyDefaultSizeAndLocation() {
        setLocation(-RESIZER_PANEL_SIZE, -RESIZER_PANEL_SIZE);
        pack();
    }

}

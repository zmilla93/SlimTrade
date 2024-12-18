package github.zmilla93.gui.menubar;

import github.zmilla93.core.chatparser.IChatScannerToggleListener;
import github.zmilla93.core.chatparser.IDndListener;
import github.zmilla93.core.chatparser.IParserLoadedListener;
import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.enums.MenubarStyle;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.core.utility.TradeUtil;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.windows.BasicDialog;
import github.zmilla93.modules.saving.ISaveListener;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.IFontChangeListener;
import github.zmilla93.modules.theme.listeners.IThemeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;

public class MenubarDialog extends BasicDialog implements ISaveListener, IFontChangeListener, IThemeListener, IParserLoadedListener, IChatScannerToggleListener, IDndListener {

    private JButton optionsButton;
    private JButton chatScannerButton;
    private JButton historyButton;
    private JButton hideoutButton;
    private JButton dndButton;
    private JButton exitButton;
    private final Component horizontalSeparator;
    private final Component verticalSeparator;
    private static final int EXIT_INSET = 8;
    private static final String DND_ENABLED = "DND is On";
    private static final String DND_DISABLED = "DND is Off";
    boolean dnd;

    private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

    public MenubarDialog() {
        horizontalSeparator = Box.createHorizontalStrut(EXIT_INSET);
        verticalSeparator = Box.createVerticalStrut(EXIT_INSET);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);

        rebuild();

        ThemeManager.addFontListener(this);
        ThemeManager.addThemeListener(this);
        SaveManager.settingsSaveFile.addListener(this);
        FrameManager.chatScannerWindow.addChatScannerToggleListener(this);
    }

    private void addListeners() {
        optionsButton.addActionListener(e -> FrameManager.optionsWindow.setVisible(!FrameManager.optionsWindow.isVisible()));
        historyButton.addActionListener(e -> FrameManager.historyWindow.setVisible(!FrameManager.historyWindow.isVisible()));
        chatScannerButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    FrameManager.chatScannerWindow.setVisible(!FrameManager.chatScannerWindow.isVisible());
                else if (e.getButton() == MouseEvent.BUTTON3) FrameManager.chatScannerWindow.toggleSearch();
            }
        });
        hideoutButton.addActionListener(e -> POEInterface.pasteWithFocus("/hideout"));
        dndButton.addActionListener(e -> POEInterface.pasteWithFocus("/dnd"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void rebuild() {
        if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.ICON) buildIconButtons();
        else buildTextButtons();
        // FIXME : Update DND Button
//        if (App.chatParser != null) updateDndButton();
        updateScannerButton();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    private void buildIconButtons() {
        buttonPanel.removeAll();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        optionsButton = new IconButton(DefaultIcon.COG);
        historyButton = new IconButton(DefaultIcon.HISTORY);
        chatScannerButton = new IconButton(DefaultIcon.SCANNER_OFF);
        hideoutButton = new IconButton(DefaultIcon.HOME);
        dndButton = new IconButton(DefaultIcon.TAG);
        exitButton = new IconButton(DefaultIcon.POWER);

        for (Component comp : getOrderedComponents()) buttonPanel.add(comp);
        addListeners();
        pack();
    }

    private void buildTextButtons() {
        buttonPanel.removeAll();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;

        optionsButton = new MenubarButton("Options");
        historyButton = new MenubarButton("History");
        chatScannerButton = new MenubarButton("Chat Scanner");
        hideoutButton = new MenubarButton("Hideout");
        dndButton = new MenubarButton(DND_DISABLED);
        exitButton = new MenubarButton("Exit");

        for (Component comp : getOrderedComponents()) {
            buttonPanel.add(comp, gc);
            gc.gridy++;
        }
        addListeners();
        pack();
    }

    private Component[] getOrderedComponents() {
        Anchor anchor = SaveManager.overlaySaveFile.data.menubarAnchor;
        Component[] components;
        components = new Component[]{
                optionsButton, historyButton,
                chatScannerButton, hideoutButton, dndButton,
                horizontalSeparator, verticalSeparator, exitButton};
        boolean reverse = false;
        if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.ICON && (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT))
            reverse = true;
        else if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.TEXT && (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT))
            reverse = true;
        if (reverse) Collections.reverse(Arrays.asList(components));
        return components;
    }

    private void handleResize() {
        pack();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    @Override
    public void onFontChanged() {
        handleResize();
    }

    @Override
    public void onThemeChange() {
        handleResize();
    }

    private void updateDndButton() {
        SwingUtilities.invokeLater(() -> {
            if (dndButton instanceof IconButton) {
                IconButton dndIconButton = (IconButton) dndButton;
                if (dnd) dndIconButton.setIcon(DefaultIcon.VOLUME_MUTE);
                else dndIconButton.setIcon(DefaultIcon.VOLUME_DOWN);
            } else {
                if (dnd) dndButton.setText(DND_ENABLED);
                else dndButton.setText(DND_DISABLED);
            }
            handleResize();
        });
    }

    private void updateScannerButton() {
        switch (SaveManager.settingsSaveFile.data.menubarStyle) {
            case TEXT:
                if (SaveManager.chatScannerSaveFile.data.searching)
                    chatScannerButton.setFont(chatScannerButton.getFont().deriveFont(Font.ITALIC));
                else
                    chatScannerButton.setFont(chatScannerButton.getFont().deriveFont(Font.PLAIN));
                break;
            case ICON:
                if (SaveManager.chatScannerSaveFile.data.searching)
                    ((IconButton) chatScannerButton).setIcon(DefaultIcon.SCANNER_ON);
                else
                    ((IconButton) chatScannerButton).setIcon(DefaultIcon.SCANNER_OFF);
                break;
        }
        pack();
    }


    @Override
    public void onChatScannerToggle(boolean state) {
        updateScannerButton();
    }

    @Override
    public void onDndToggle(boolean state, boolean loaded) {
        dnd = state;
        if (!loaded) return;
        updateDndButton();
    }

    @Override
    public void onParserLoaded(boolean dnd) {
        this.dnd = dnd;
        updateDndButton();
    }

    @Override
    public void onSave() {
        rebuild();
    }

}

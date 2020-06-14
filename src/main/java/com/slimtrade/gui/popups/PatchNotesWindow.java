package com.slimtrade.gui.popups;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.updating.ReleaseData;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomScrollPane;
import com.slimtrade.gui.custom.CustomTextPane;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.util.Objects;

public class PatchNotesWindow extends JFrame implements IColorable {

    JPanel container = new JPanel(new GridBagLayout());
    JTextPane textPane = new CustomTextPane();
    JScrollPane scrollPane = new CustomScrollPane(container);
    JPanel bufferPanel = new JPanel(new GridBagLayout());
    JComboBox<String> comboBox = new CustomCombo<>();

    public PatchNotesWindow() {

        System.out.println(App.updateManager.fetchReleaseData());
        for (ReleaseData data : App.updateManager.getReleaseData()) {
            System.out.println("RELEASE : " + data.tag);
        }

        setTitle("SlimTrade - Patch Notes");
        setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/default/tagx64.png"))).getImage());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setSize(400, 400);
        textPane.setEditable(false);
        textPane.setOpaque(false);
        textPane.setContentType("text/html");
        textPane.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                TradeUtility.openLink(e.getURL());
            }
        });

        // Components
        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setOpaque(false);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        JButton githubButton = new BasicButton("Github");
        JButton discordButton = new BasicButton("Discord");
        JButton donateButton = new ConfirmButton("Donate");
        githubButton.addActionListener(e -> {
            TradeUtility.openLink(References.GITHUB);
        });
        discordButton.addActionListener(e -> {
            TradeUtility.openLink(References.DISCORD);
        });
        donateButton.addActionListener(e -> {
            TradeUtility.openLink(References.PAYPAL);
        });

        // Build UI
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        int i = 10;
        gc.insets = new Insets(i, i, i, i);
        bufferPanel.add(textPane, gc);
        gc.insets = new Insets(0, 0, 0, 0);

        buttonPanel.add(githubButton, gc);
        gc.insets.left = 20;
        gc.gridx++;
        buttonPanel.add(discordButton, gc);
        gc.gridx++;
        buttonPanel.add(donateButton, gc);

        gc.gridx = 0;
        gc.insets.left = 0;

        innerPanel.add(buttonPanel, gc);
        gc.insets.top = 20;
        gc.gridy++;
        innerPanel.add(bufferPanel, gc);
        i = 40;
        gc.gridy = 0;
        gc.insets = new Insets(i, i, i, i);
        container.add(innerPanel, gc);
        setMaximumSize(TradeUtility.screenSize);
        String s = "\\r\\n## Stach Searcher\\r\\n- Create a list of search terms that can be quickly entered into your stash tab.\\r\\n" +
                "- *Color coding to match stash tab colors.*\\r\\n" +
                "- **Color coding to match stash tab colors.**\\r\\n" +
                "- ***Options > Stash Searcher [test](https://github.com).***\\r\\n" +
                "\\r\\n## Custom Cheat Sheets\\r\\n" +
                "- Custom *images* can **now** be *displayed* within ***the*** SlimTrade *app*lication.\\r\\n" +
                "- ***Options > Cheat Sheets***\\r\\n\\r\\n" +
                "## Auto Updater\\r\\n" +
                "- The 'SlimTrade.jar' file now acts as a launcher and automatically aquire new versions of the program on launch.\\r\\n" +
                "- *Option* is* available* to show *you* patch *notes* and a confirm message before updating. Options > General > Updates\\r\\" +
                "n- The 'slimtrade-core.jar' file can still be run as the standalone trade program if necessary.\\r\\n\\r\\n" +
                "## Window Improvements\\r\\n" +
                "- Messages can now be collapsed, as well as fade out to a set transparency. Options > General > Message Popups\\r\\n" +
                "- All windows can now be pinned to save their size and location between restarts.\\r\\n" +
                "- Right clicking the pin button resets to default size and location, middle click to just center.\\r\\n" +
                "- Holding shift while moving a window will prevent it from leaving the main monitor.\\r\\n\\r\\n" +
                "## Bug Fixes\\r\\n" +
                "+ Tutorial and ReadME need updating\\r\\n" +
                "- Hotkeys will be ignored while tabbed out (except for quick paste).\\r\\n" +
                "- Added duplication check for chat scanner messages.\\r\\n" +
                "- Added quick close for chat scanner messages.\\r\\n" +
                "- Icon button hover border now matches text color.\\r\\n" +
                "- Resetting the UI now correctly reverts message expand direction.\\r\\n" +
                "- If stash search is used outside of stash, message will be sent in local instead of global.\\r\\n" +
                "- Fixed menu selection buttons getting stuck in a painting loop when hovered while selected.\\r\\n" +
                "- Fixed a crash that could occur if the client path was changed during initial setup phase.\\r\\n\\r\\n";
//                "## How to Install/Update\\r\\n" +
//                "- [Ensure you have Java](https://www.java.com/)\\r\\n" +
//                "- If you have an existing version of SlimTrade already, delete your current 'SlimTrade.jar' file\\r\\n" +
//                "- **Download and run 'SlimTrade.jar' from below**";

        textPane.setText(App.updateManager.getReleaseData().get(0).getColorPatchNotes(ColorManager.TEXT));
        textPane.setCaretPosition(0);
        pack();
        FrameManager.fitWindowToScreen(this);
    }

    @Override
    public void updateColor() {
        getContentPane().setBackground(ColorManager.LOW_CONTRAST_1);
        container.setBackground(ColorManager.LOW_CONTRAST_1);
        bufferPanel.setBackground(ColorManager.BACKGROUND);
        bufferPanel.setBorder(ColorManager.BORDER_TEXT);
        textPane.setForeground(Color.ORANGE);
    }
}

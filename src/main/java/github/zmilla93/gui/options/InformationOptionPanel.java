package github.zmilla93.gui.options;

import github.zmilla93.core.References;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;

public class InformationOptionPanel extends AbstractOptionPanel {

    // Utility
    private final JButton tutorialButton = new JButton("Tutorial");
    private final JButton patchNotesButton = new JButton("Patch Notes");
    private final JButton roadmapButton = new JButton("Roadmap");
    private final JButton settingsFolderButton = new JButton("Settings Folder");
    private final JButton logsFolderButton = new JButton("Logs Folder");
//    private final JButton openClientButton = new JButton("Open Client.txt");

    // Help
    private final JButton troubleshootingButton = new JButton("Troubleshooting");
    private final JButton bugReportButton = new JButton("Report Bug");

    // Links
    private final JButton githubButton = new JButton("GitHub");
    private final JButton discordButton = new JButton("Discord");


    public InformationOptionPanel() {

        JPanel utilityPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = getGC();
        utilityPanel.add(tutorialButton, gc);
        gc.gridy++;
        utilityPanel.add(patchNotesButton, gc);
        gc.gridy++;
        utilityPanel.add(roadmapButton, gc);
        gc.gridy++;
        utilityPanel.add(settingsFolderButton, gc);
        gc.gridy++;
        utilityPanel.add(logsFolderButton, gc);
        gc.gridy++;

        JPanel helpPanel = new JPanel(new GridBagLayout());
        gc = getGC();
        helpPanel.add(troubleshootingButton, gc);
        gc.gridy++;
        helpPanel.add(bugReportButton, gc);
        gc.gridy++;

        JPanel linksPanel = new JPanel(new GridBagLayout());
        gc = getGC();
        linksPanel.add(discordButton, gc);
        gc.gridy++;
        linksPanel.add(githubButton, gc);
        gc.gridy++;

        addHeader("Utility");
        addComponent(utilityPanel);
        addVerticalStrut();
        addHeader("Help");
        addComponent(helpPanel);
        addVerticalStrut();
        addHeader("Links");
        addComponent(linksPanel);

        addListeners();
    }

    private void addListeners() {
        tutorialButton.addActionListener(e -> FrameManager.tutorialWindow.setVisible(true));
        patchNotesButton.addActionListener(e -> {
            // FIXME : Make sure patch notes have been fetched, probably using another thread.
            FrameManager.patchNotesWindow.setVisible(true);
        });
//        openClientButton.addActionListener(e -> ZUtil.openFile(SaveManager.settingsSaveFile.data.clientPath));
        settingsFolderButton.addActionListener(e -> ZUtil.openExplorer(SaveManager.getSaveDirectory().toString()));
        logsFolderButton.addActionListener(e -> ZUtil.openExplorer(SaveManager.getLogsDirectory().toString()));
        troubleshootingButton.addActionListener(e -> ZUtil.openLink(References.FAQ_URL));
        bugReportButton.addActionListener(e -> ZUtil.openLink(References.GITHUB_ISSUES_URL));
        githubButton.addActionListener(e -> ZUtil.openLink(References.GITHUB_URL));
        discordButton.addActionListener(e -> ZUtil.openLink(References.DISCORD_INVITE));
    }

    private GridBagConstraints getGC() {
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        return gc;
    }

}

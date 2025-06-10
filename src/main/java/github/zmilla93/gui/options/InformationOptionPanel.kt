package github.zmilla93.gui.options

import github.zmilla93.core.References
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.utility.ZUtil.getGC
import github.zmilla93.core.utility.ZUtil.openExplorer
import github.zmilla93.core.utility.ZUtil.openLink
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel

class InformationOptionPanel : AbstractOptionPanel() {
    // Utility
    private val tutorialButton = JButton("Tutorial")
    private val patchNotesButton = JButton("Patch Notes").onClick { FrameManager.patchNotesWindow.isVisible = true }
    private val roadmapButton = JButton("Roadmap").onClick { FrameManager.roadMapWindow.isVisible = true }
    private val settingsFolderButton = JButton("Settings Folder")
    private val logsFolderButton = JButton("Logs Folder")

    //    private final JButton openClientButton = new JButton("Open Client.txt");
    // Help
    private val troubleshootingButton = JButton("Troubleshooting")
    private val bugReportButton = JButton("Report Bug")

    // Links
    private val githubButton = JButton("GitHub")
    private val discordButton = JButton("Discord")


    init {
        val utilityPanel = JPanel(GridBagLayout())
        var gc: GridBagConstraints = this.gC
        utilityPanel.add(tutorialButton, gc)
        gc.gridy++
        utilityPanel.add(patchNotesButton, gc)
        gc.gridy++
        utilityPanel.add(roadmapButton, gc)
        gc.gridy++
        utilityPanel.add(settingsFolderButton, gc)
        gc.gridy++
        utilityPanel.add(logsFolderButton, gc)
        gc.gridy++

        val helpPanel = JPanel(GridBagLayout())
        gc = this.gC
        helpPanel.add(troubleshootingButton, gc)
        gc.gridy++
        helpPanel.add(bugReportButton, gc)
        gc.gridy++

        val linksPanel = JPanel(GridBagLayout())
        gc = this.gC
        linksPanel.add(discordButton, gc)
        gc.gridy++
        linksPanel.add(githubButton, gc)
        gc.gridy++

        addHeader("Utility")
        addComponent(utilityPanel)
        addVerticalStrut()
        addHeader("Help")
        addComponent(helpPanel)
        addVerticalStrut()
        addHeader("Links")
        addComponent(linksPanel)

        addListeners()
    }

    private fun addListeners() {
        tutorialButton.addActionListener(ActionListener { e: ActionEvent? -> FrameManager.tutorialWindow.setVisible(true) })
        patchNotesButton.addActionListener(ActionListener { e: ActionEvent? ->
            // FIXME : Make sure patch notes have been fetched, probably using another thread.
            FrameManager.patchNotesWindow.setVisible(true)
        })
        //        openClientButton.addActionListener(e -> ZUtil.openFile(SaveManager.settingsSaveFile.data.clientPath));
        settingsFolderButton.addActionListener(ActionListener { e: ActionEvent? ->
            openExplorer(
                SaveManager.getSaveDirectory().toString()
            )
        })
        logsFolderButton.addActionListener(ActionListener { e: ActionEvent? ->
            openExplorer(
                SaveManager.getLogsDirectory().toString()
            )
        })
        troubleshootingButton.addActionListener(ActionListener { e: ActionEvent? -> openLink(References.FAQ_URL) })
        bugReportButton.addActionListener(ActionListener { e: ActionEvent? -> openLink(References.GITHUB_ISSUES_URL) })
        githubButton.addActionListener(ActionListener { e: ActionEvent? -> openLink(References.GITHUB_URL) })
        discordButton.addActionListener(ActionListener { e: ActionEvent? -> openLink(References.DISCORD_INVITE) })
    }

    private val gC: GridBagConstraints
        get() {
            val gc = getGC()
            gc.weightx = 1.0
            gc.fill = GridBagConstraints.HORIZONTAL
            return gc
        }
}

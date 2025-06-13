package github.zmilla93.gui.windows

import github.zmilla93.App
import github.zmilla93.core.References
import github.zmilla93.core.utility.ClipboardManager
import github.zmilla93.core.utility.Platform
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.BoxPanel
import github.zmilla93.gui.components.ComponentPanel
import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

/**
 * A crash report window to show the user if an exception is encountered.
 * Includes a stack track and info on how to report.
 */
class CrashReportWindow(error: Exception) : JFrame() {

    val panel = BoxPanel()
    val crashReportText = crashReportText(error)
    val textArea = JTextArea(crashReportText)
    val copyReportButton = JButton("Copy Crash Report").onClick { ClipboardManager.setContents(crashReportText) }
    val githubButton = JButton("GitHub").onClick { ZUtil.openLink(References.GITHUB_ISSUES_URL) }
    val discordButton = JButton("Discord").onClick { ZUtil.openLink(References.DISCORD_INVITE) }

    init {
        title = "SlimTrade Crashed"
        isAlwaysOnTop = true
        defaultCloseOperation = EXIT_ON_CLOSE

        if (App.updateManager.isUpdateAvailable) {
            panel.header("Update available!")
            panel.label("SlimTrade crashed, but there is a new update available.")
            panel.addLeft(JButton("Install SlimTrade ${App.updateManager.latestRelease}").onClick {
                App.updateManager.runUpdateProcessFromSwing()
            })
        } else {
            panel.header("Crash Report")
            panel.label("Post this crash report on GitHub or Discord.")
        }

        val southPanel = JPanel(BorderLayout())
        southPanel.add(copyReportButton, BorderLayout.WEST)
        southPanel.add(ComponentPanel(githubButton, discordButton), BorderLayout.EAST)

        layout = BorderLayout()
        add(panel, BorderLayout.NORTH)
        add(CustomScrollPane(textArea), BorderLayout.CENTER)
        add(southPanel, BorderLayout.SOUTH)
        textArea.caretPosition = 0
    }

    /** Turns an [Exception] into a full crash report. */
    fun crashReportText(e: Exception): String {
        val builder = StringBuilder()
        builder.append("SlimTrade v${App.getAppInfo().appVersion} (${Platform.current})\n")
        builder.append("Message: ${e.message}\n")
        e.stackTrace.forEach { builder.append(it).append("\n") }
        return builder.toString()
    }

    companion object {

        /** Creates and shows a new crash report dialog. */
        fun showCrashReport(e: Exception) {
            SwingUtilities.invokeLater {
                val window = CrashReportWindow(e)
                window.pack()
                window.size = Dimension(620, 400)
                window.isVisible = true
                window.setLocationRelativeTo(null)
            }
        }

    }

}
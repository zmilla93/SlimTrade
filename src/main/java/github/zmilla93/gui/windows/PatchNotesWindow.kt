package github.zmilla93.gui.windows

import github.zmilla93.App
import github.zmilla93.core.References
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.MarkdownParser
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.BufferPanel
import github.zmilla93.gui.components.CardPanel
import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.gui.components.LimitCombo
import github.zmilla93.gui.listening.IDefaultSizeAndLocation
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.gui.managers.LaunchPopupManager
import github.zmilla93.modules.updater.PatchNotesEntry
import github.zmilla93.modules.updater.PatchNotesGroup
import github.zmilla93.modules.updater.PatchNotesManager
import github.zmilla93.modules.updater.data.AppVersion
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagLayout
import java.util.*
import javax.swing.*
import javax.swing.event.HyperlinkEvent

class PatchNotesWindow : CustomDialog("Patch Notes"), IDefaultSizeAndLocation {

    private val groupPatchNotes = false
    private val byPatchCombo = LimitCombo<PatchNotesEntry>()
    private val byMinorCombo = LimitCombo<PatchNotesGroup>()
    private val currentCombo = if (groupPatchNotes) byMinorCombo else byPatchCombo
    private val textPane = JTextPane()

    private val githubButton = JButton("GitHub")
    private val discordButton = JButton("Discord")
    private val donateButton = JButton("Donate")
    private val cardPanel = CardPanel()

    // FIXME : Remove when roadmap is added
    // Announcement Panel
    private var showAnnouncement = false
    private val announcementPanel: JPanel = createAnnouncementPanel()
    private val patchNotesPanel: JPanel
    private var toggleButton = JButton()

    companion object {
        private val PREFIX =
            "**Enjoying the app? Consider supporting on [Patreon](" + References.PATREON_URL + ") or [PayPal](" + References.PAYPAL_URL + ")!**<br>"
        private val SUFFIX =
            "*Want to report a bug or give feedback? Post on [GitHub](" + References.GITHUB_ISSUES_URL + ") or join the [Discord](" + References.DISCORD_INVITE + ")!*"
    }

    init {
        pinButton.isVisible = false
        // FIXME : Combine local and remote
        val remoteEntries = App.updateManager.getPatchNotes(App.getAppInfo().appVersion)
        val localEntries = PatchNotesManager.getPatchNotes()
        localEntries.forEach {
            byPatchCombo.addItem(it)
        }
        PatchNotesManager.patchNotesMyMinor().entries.sortedBy { it.key }.reversed().forEach {
            byMinorCombo.addItem(PatchNotesGroup(it.key, it.value))
        }

        // Text Pane
        textPane.isEditable = false
        textPane.setContentType("text/html")
        val scrollPane: JScrollPane = CustomScrollPane(textPane)

        // Button Panel
        val buttonPanel = JPanel(BorderLayout())
        val githubWrapperPanel = JPanel(GridBagLayout())
        githubWrapperPanel.add(githubButton)
        buttonPanel.add(githubButton, BorderLayout.WEST)
        buttonPanel.add(discordButton, BorderLayout.CENTER)
        buttonPanel.add(donateButton, BorderLayout.EAST)

        val buttonWrapperPanel = JPanel(BorderLayout())
        buttonWrapperPanel.add(buttonPanel, BorderLayout.EAST)

        // Controls
        val controlsPanel = JPanel(BorderLayout())
        controlsPanel.add(buttonWrapperPanel, BorderLayout.WEST)
        controlsPanel.add(currentCombo, BorderLayout.EAST)

        // Build Panel
        patchNotesPanel = JPanel(BorderLayout())
        patchNotesPanel.add(controlsPanel, BorderLayout.NORTH)
        patchNotesPanel.add(scrollPane, BorderLayout.CENTER)

        toggleButton = JButton("Toggle")
        val wrapperPanel = JPanel(BorderLayout())
        wrapperPanel.add(cardPanel, BorderLayout.CENTER)
        wrapperPanel.add(JButton("Flip"), BorderLayout.SOUTH)
        cardPanel.add(patchNotesPanel)
        cardPanel.add(announcementPanel)
        contentPanel.setLayout(BorderLayout())
        contentPanel.add(cardPanel, BorderLayout.CENTER)
        contentPanel.add(toggleButton, BorderLayout.SOUTH)

        // Finalize
        addListeners()
        updateSelectedPatchNotes()
        currentCombo.requestFocus()

        showCurrentPanel()
        minimumSize = Dimension(400, 400)
        pack()

        LaunchPopupManager.registerFrameTrigger(this)
    }

    private fun togglePanel() {
        showAnnouncement = !showAnnouncement
        showCurrentPanel()
    }

    private fun showCurrentPanel() {
        if (showAnnouncement) {
            toggleButton.setText("Show Patch Notes")
            cardPanel.showCard(announcementPanel)
        } else {
            toggleButton.setText("Show Announcement")
            cardPanel.showCard(patchNotesPanel)
        }
    }

    private fun createAnnouncementPanel(): JPanel {
        val panel = JPanel(BorderLayout())
        val textArea = JTextPane()
        textArea.isEditable = false
        textArea.setContentType("text/html")
        textArea.text = "Hi there! ZMilla, author of SlimTrade here,<br><br>" +
                "My desktop PC died, money is tight, and I can't play POE for a bit. If everyone reading this donated just one dollar... (you know how this goes)<br><br>" +
                "Or don't! That's completely fine. Just understand that I don't want to feel obligated to work on SlimTrade when I can't play POE myself.<br><br>" +
                "<b>I will still maintain SlimTrade with bug fixes, but new features & QOL are on hold.</b><br><br>" +
                "Thanks for reading! Stay sane, exile."
        panel.add(BufferPanel(JLabel("Annoying Announcement :^)"), 4), BorderLayout.NORTH)
        val donationButton = JButton("Donate")
        panel.add(CustomScrollPane(textArea), BorderLayout.CENTER)
        panel.add(donationButton, BorderLayout.SOUTH)
        donationButton.addActionListener { openDonationWindow() }
        return panel
    }

    private fun addListeners() {
        toggleButton.addActionListener { togglePanel() }
        currentCombo.addActionListener { updateSelectedPatchNotes() }
        textPane.addHyperlinkListener { e: HyperlinkEvent ->
            if (e.eventType == HyperlinkEvent.EventType.ACTIVATED) {
                ZUtil.openLink(e.url.toString())
            }
        }
        githubButton.addActionListener { ZUtil.openLink(References.GITHUB_URL) }
        discordButton.addActionListener { ZUtil.openLink(References.DISCORD_INVITE) }
        donateButton.addActionListener { openDonationWindow() }
    }

    private fun openDonationWindow() {
        FrameManager.optionsWindow.setVisible(true)
        FrameManager.optionsWindow.toFront()
        FrameManager.optionsWindow.showDonationPanel()
    }

    private fun updateSelectedPatchNotes() {
        // FIXME : Remove null after resource reading fix
        var patchNotes: String? = null
        if (groupPatchNotes) {
            val group = byMinorCombo.selectedItem as PatchNotesGroup
            patchNotes = group.combinedPatchNotes
        } else {
            // FIXME : Remove this check after resource reading fix
            val entry = byPatchCombo.selectedItem as? PatchNotesEntry
            if (entry != null)
                patchNotes = entry.text
        }
        textPane.text = patchNotes
        textPane.caretPosition = 0
    }

    // FIXME : Move to manager
    private fun getCleanPatchNotes(version: AppVersion?, body: String, addExtraInfo: Boolean): String {
        val lines = body.split("(\\n|\\\\r\\\\n)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = StringBuilder()
        builder.append("<h1>SlimTrade ").append(version).append("</h1>")
        if (addExtraInfo) builder.append(MarkdownParser.getHtmlFromMarkdown(PREFIX))
        for (s in lines) {
            if (s.lowercase(Locale.getDefault()).contains("how to install")) {
                break
            }
            builder.append(MarkdownParser.getHtmlFromMarkdown(s))
        }
        if (addExtraInfo) builder.append(MarkdownParser.getHtmlFromMarkdown(SUFFIX))
        return builder.toString()
    }

    override fun setVisible(visible: Boolean) {
        super.setVisible(visible)
        if (visible) getRootPane().requestFocus()
    }

    override fun applyDefaultSizeAndLocation() {
        size = Dimension(600, 600)
        POEWindow.centerWindow(this)
    }

}

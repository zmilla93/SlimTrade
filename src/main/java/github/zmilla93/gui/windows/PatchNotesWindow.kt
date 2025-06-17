package github.zmilla93.gui.windows

import github.zmilla93.core.References
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.CenterPanel
import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.gui.components.LimitCombo
import github.zmilla93.gui.listening.IDefaultSizeAndLocation
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.updater.PatchNotesEntry
import github.zmilla93.modules.updater.PatchNotesGroup
import github.zmilla93.modules.updater.PatchNotesManager
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import github.zmilla93.modules.zswing.extensions.StyleExtensions.italic
import io.github.zmilla93.gui.components.cardpanel.CardPanel
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
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

    // FIXME : Remove
    private val cardPanel = CardPanel()

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
//        val buttonPanel = JPanel(BorderLayout())
//        val githubWrapperPanel = JPanel(GridBagLayout())
//        githubWrapperPanel.add(githubButton)
//        buttonPanel.add(githubButton, BorderLayout.WEST)
//        buttonPanel.add(discordButton, BorderLayout.CENTER)
//        buttonPanel.add(donateButton, BorderLayout.EAST)

//        val buttonWrapperPanel = JPanel(BorderLayout())
//        buttonWrapperPanel.add(buttonPanel, BorderLayout.EAST)

        // Controls
        val controlsPanel = JPanel(BorderLayout())
//        controlsPanel.add(buttonWrapperPanel, BorderLayout.WEST)
        controlsPanel.add(currentCombo, BorderLayout.EAST)

        // Build Panel
        patchNotesPanel = JPanel(BorderLayout())
        patchNotesPanel.add(controlsPanel, BorderLayout.NORTH)
        patchNotesPanel.add(scrollPane, BorderLayout.CENTER)

        val roadmapButton = JButton("View Roadmap").onClick {
            FrameManager.roadMapWindow.isVisible = true
            isVisible = false
        }

        val buttons = JPanel(GridBagLayout())
        val gc = ZUtil.getGC()
        gc.fill = GridBagConstraints.BOTH
        gc.weightx = 1.0
        buttons.add(githubButton, gc)
        gc.gridx++
        buttons.add(discordButton, gc)
        gc.gridx++
        buttons.add(roadmapButton, gc)
        val bottomPanel = JPanel(BorderLayout())
        val bugLabel = JLabel("Want to report a bug or give feedback?").italic()
        bottomPanel.add(CenterPanel(bugLabel), BorderLayout.NORTH)
        bottomPanel.add(buttons, BorderLayout.CENTER)
//        bottomPanel.add(, BorderLayout.SOUTH)

        toggleButton = JButton("Toggle")
        val wrapperPanel = JPanel(BorderLayout())
        wrapperPanel.add(cardPanel, BorderLayout.CENTER)
        wrapperPanel.add(JButton("Flip"), BorderLayout.SOUTH)
        cardPanel.add(patchNotesPanel)
        contentPanel.setLayout(BorderLayout())
        contentPanel.add(cardPanel, BorderLayout.CENTER)
        contentPanel.add(bottomPanel, BorderLayout.SOUTH)

        // Finalize
        addListeners()
        updateSelectedPatchNotes()
        currentCombo.requestFocus()

        minimumSize = Dimension(400, 400)
        pack()
    }


    private fun addListeners() {
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
        var patchNotes: String? = null
        if (groupPatchNotes) {
            val group = byMinorCombo.selectedItem as? PatchNotesGroup
            if (group != null) patchNotes = group.combinedPatchNotes
        } else {
            val entry = byPatchCombo.selectedItem as? PatchNotesEntry
            if (entry != null) patchNotes = entry.html
        }
        textPane.text = patchNotes
        textPane.caretPosition = 0
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

package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.BoxPanel
import github.zmilla93.gui.components.ComponentPanel
import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.gui.components.HTMLLabel
import github.zmilla93.gui.donate.PatreonTier
import github.zmilla93.gui.donate.Supporters
import github.zmilla93.gui.listening.IDefaultSizeAndLocation
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import github.zmilla93.modules.zswing.extensions.PanelExtensions.fitParentWidth
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import github.zmilla93.modules.zswing.theme.ThemeColorBlind
import io.github.zmilla93.modules.theme.UIProperty.FontColorExtensions.textColor
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.*
import javax.swing.*

class RoadmapWindow : CustomDialog("Roadmap"), IDefaultSizeAndLocation {

    val pollLink =
        "https://docs.google.com/forms/d/e/1FAIpQLSfT3Q9eVN-P2dLCeld119HMPWKjGf0lyo0yCS_IBd1y7jCaEQ/viewform?usp=dialog"

    val defaultSize = Dimension(600, 840)

    val pollButton = JButton("Take Poll (8 Questions)").onClick {
        ZUtil.openLink(pollLink)
    }

    init {
        pinButton.isVisible = false
        val roadmapPanel = RoadmapPanel()
        // Content Panel
        val contentPanel = contentPane
        contentPanel.layout = BorderLayout()

        val southPanel = JPanel(GridBagLayout())
        val gc = ZUtil.getGC()
        val inset = 1
        gc.insets = Insets(inset, inset, inset, inset)
        gc.fill = GridBagConstraints.HORIZONTAL
        gc.weightx = 1.0

//        val pollBox = JPanel(GridBagLayout())
//        val pollgc = ZUtil.getGC()
//        pollgc.weightx = 1.0
//        pollBox.add(JLabel("Thoughts? I want your feedback!").textColor(ThemeColorBlind.GREEN), pollgc)
//        pollgc.gridx++
//        pollgc.fill = GridBagConstraints.HORIZONTAL
//        pollBox.add(pollButton, pollgc)

        val southBox = BoxPanel(0)
        southBox.strut(2)
        southBox.addCenter(JLabel("Thoughts? I want your feedback!").textColor(ThemeColorBlind.GREEN))
        southBox.strut(2)
        southBox.add(pollButton)

        southPanel.add(southBox, gc)
        gc.gridy++
        gc.insets.top = 0
        southPanel.add(JButton("View Patch Notes").onClick {
            isVisible = false
            FrameManager.patchNotesWindow.isVisible = true
        }, gc)

        val wrapper = JPanel(BorderLayout())
        wrapper.add(roadmapPanel, BorderLayout.NORTH)
        contentPanel.add(HeaderPanel(), BorderLayout.NORTH)
        contentPanel.add(CustomScrollPane(wrapper), BorderLayout.CENTER)
        contentPanel.add(southPanel, BorderLayout.SOUTH)
        pack()
    }

    inner class HeaderPanel : BoxPanel() {
        init {
            val gap = 10
            strut(gap)
//            val combedLabel = JLabel("SlimTrade Roadmap").fontSize(30).bold()
//            combedLabel.icon = ImageIcon(ImageUtil.scaledImage("/league/sota/POELogo.png", 20))
            val headerIconLabel = JLabel()
            val headerTextLabel = JLabel("SlimTrade Roadmap").fontSize(40).bold()
            headerIconLabel.icon = ImageIcon(ImageUtil.scaledImage("/league/sota/POELogo.png", 20))
            addCenter(ComponentPanel(headerIconLabel, headerTextLabel))
            strut(gap)
        }
    }

    inner class RoadmapPanel : BoxPanel() {

        val improvementIdeas = arrayOf(
            Entry("Currency stack conversion, ie \"173c [8s+13]\""),
            Entry("Macros - Switch chat channel, send chat messages, custom commands"),
            Entry("Cheat Sheets - Borderless, resizable, transparency, drag & drop"),
            Entry("Chat Scanner - Regex support"),
//            Entry("Menubar customization"),
            Entry("Window system rework"),
//            Entry("Discord Revamp"),
            Entry("UI improvements and cleanup"),
            Entry("Dozens of small QOL, UI improvements, and bug fixes"),
        )

        val newFeatureIdeas = arrayOf(
            Entry("Rebrand with a new name & new focus on 'tool overlay' rather than just trading"),
            Entry("Mapping speedrun timer"),
            Entry("URL Bookmarks"),
            Entry("Quick Search - Search common item names, open link to wiki, trade site, etc"),
            Entry("Filter Manager (for people not using syncing)"),
//            Entry("League specific tools"),
//            Entry("Integration with other tools"),
//            Entry("Feature Toggle - Hide unused features to free UI space"),
//            Entry("Updates to existing features"),
            Entry("Alert System for zone changes, npc/boss dialog, character progression"),
//            Entry("UI Translations"),
            Entry("Helper tools for leveling, league mechanics, etc"),
            Entry("Betrayal cheat sheet maker (likely web based)"),
            Entry("Website (feature spotlight, downloads, FAQ, guides, web tools)"),
            Entry("Possible web api interaction?"),
            Entry("UI reorganization"),
            Entry("Native App (.exe, .dbn)"),
//            Entry("Web API for dynamic info"),
//            Entry("POE API integration"),
            Entry("... and more?"),
        )

        val beingWorkedOn = arrayOf(
            Entry("Converting Java to Kotlin (Faster development, less bugs)"),
            Entry("Updating old code, fixing bugs"),
            Entry("QoL and UI improvements"),
        )

        init {
            headerSize = 20
            strutSize = 20
            leftInset = 10

            /** Patreon blurb */
            // FIXME @important: Link to donater page, not patreon directly
//            val htmlText = HTMLTextArea(
//                "<html>Software development is time consuming. An occasional dollar really adds up when everyone does it. :)<br>" +
//                        "<a href='${References.PATREON_URL}'>Consider supporting me!</a> An occasional dollar adds up when everyone does it.<br></html>"
//            )
            // FIXME : hyperlink class or extension
//            htmlText.addHyperlinkListener { e: HyperlinkEvent ->
//                if (e.eventType == HyperlinkEvent.EventType.ACTIVATED) {
//                    ZUtil.openLink(e.url.toString())
//                }
//            }

            header(PatreonTier.HEADER_TEXT)
            add(htmlLabel(PatreonTier.INFO_TEXT).fitParentWidth())
            strut(4)
            // Progress Bar
            val patreonProgressBar = JProgressBar()
            patreonProgressBar.preferredSize = Dimension(0, 10)
            patreonProgressBar.value = Supporters.getCurrentPatreonCount()
            patreonProgressBar.maximum = PatreonTier.PATRON_GOAL
            add(JLabel(PatreonTier.GOAL_TEXT).bold())
            val insetX = 20
            val insetY = 2
            addWithInset(patreonProgressBar, Insets(insetY, insetX, insetY, insetX))
            add(patreonProgressBar)
            strut(4)
            add(JButton("Support SlimTrade :)").onClick {
                FrameManager.optionsWindow.showDonationPanel()
                FrameManager.optionsWindow.isAlwaysOnTop = false
                FrameManager.optionsWindow.isAlwaysOnTop = true
            })
            strut()

            /** Road to 1.0.0. */
            header("The road to 1.0.0...")
            add(
                HTMLLabel(
                    "SlimTrade feels close to complete as a trading tool... so now what? " +
                            "I'm thinking about reworking the project to be more of a general purpose overlay tool. " +
                            "<b>This would include a new name, new features, UI rework, website, and existing feature reworks.<b>"
                ).fitParentWidth()
            )
            strutSmall()


            /** Take Poll */
//            add(pollB)
            strut()

            /** Minor Features */
//            header("Improvement Ideas")
//            label("These are ideas, but will likely happen.").textColor(ThemeColorBlind.YELLOW)
//            addEntryGroup(improvementIdeas)
//            strut()

            /** New Features */
            header("New Ideas")
            label("These are just ideas, not guarantees.").textColor(ThemeColorBlind.RED)
            addEntryGroup(newFeatureIdeas)
            strut()

            header("Working on now")
            addEntryGroup(beingWorkedOn)
//            label("This may sound boring (it is), but is needed prep work for future updates.").bold()
//                .textColor(ThemeColorBlind.YELLOW)
        }

        fun addEntryGroup(entries: Array<Entry>) {
            for (entry in entries) {
                add(HTMLLabel("- ${entry.text}").fitParentWidth())
                for (sub in entry.subentries) {
                    addWithInset(HTMLLabel("- $sub").fitParentWidth(), 20)
                }
            }
        }

    }

    override fun setVisible(visible: Boolean) {
        val wasVisible = isVisible
        // FIXME : Dynamic html has some issues on first render, this pack seems to help.
        //  Bug currently seems to appear only when showing this window after the tutorial window? idk
        if (visible) {
            val size = this.size
            pack()
            this.size = size
        }
        super.setVisible(visible)

    }

    override fun applyDefaultSizeAndLocation() {
        pack()
        minimumSize = Dimension(size.width, 600)
        size = defaultSize
        POEWindow.centerWindow(this)
    }

    /** A class for displaying an entry, with possible subentries. */
    class Entry(val text: String) {

        val subentries = ArrayList<String>()

        fun sub(text: String): Entry {
            subentries.add(text)
            return this
        }

    }

}
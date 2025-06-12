package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.BoxPanel
import github.zmilla93.gui.components.ComponentPanel
import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.gui.components.HTMLLabel
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import github.zmilla93.modules.zswing.extensions.PanelExtensions.fitParentWidth
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import github.zmilla93.modules.zswing.theme.ThemeColorBlind
import io.github.zmilla93.modules.theme.UIProperty.FontColorExtensions.textColor
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.*
import javax.swing.*

class RoadmapWindow : CustomDialog("Roadmap") {

    val defaultSize = Dimension(700, 910)

    init {
        val roadmapPanel = RoadmapPanel()
        // Content Panel
        contentPanel.layout = BorderLayout()

//        val supportButton = JButton("Support SlimTrade :)").onClick { FrameManager.optionsWindow.showDonationPanel() }

        val southPanel = JPanel(GridBagLayout())
        val gc = ZUtil.getGC()
        val inset = 2
        gc.insets = Insets(inset, inset, inset, inset)
        gc.fill = GridBagConstraints.BOTH
        gc.weightx = 1.0

        southPanel.add(JButton("Patch Notes").onClick {
            isVisible = false
            FrameManager.patchNotesWindow.isVisible = true
        }, gc)
//        gc.gridx++
//        gc.insets.left = 0
//        southPanel.add(supportButton, gc)

        val wrapper = JPanel(BorderLayout())
        wrapper.add(roadmapPanel, BorderLayout.NORTH)
        contentPanel.add(HeaderPanel(), BorderLayout.NORTH)
        contentPanel.add(CustomScrollPane(wrapper), BorderLayout.CENTER)
        contentPanel.add(southPanel, BorderLayout.SOUTH)
        pack()
        minimumSize = Dimension(size.width, 600)
        size = defaultSize
        POEWindow.centerWindow(this)
    }

    class HeaderPanel : BoxPanel() {
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

    class RoadmapPanel : BoxPanel() {

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
            Entry("Filter Manager - Drag & drop new filters, delete old filters, auto unzip"),
            Entry("URL Bookmarks"),
            Entry("Quick Search - Search common item names, open link to wiki, trade site, etc"),
//            Entry("League specific tools"),
//            Entry("Integration with other tools"),
//            Entry("Feature Toggle - Hide unused features to free UI space"),
            Entry("Alert System for zone changes, npc/boss dialog, character progression"),
//            Entry("UI Translations"),
            Entry("Betrayal cheat sheet maker (likely web based)"),
            Entry("Website (feature spotlight, downloads, FAQ, guides)"),
            Entry("Possible web api interaction?"),
//            Entry("Web API for dynamic info"),
//            Entry("POE API integration"),
            Entry("Native App (.exe, .dbn)"),
            Entry("... and more?"),
        )

        val beingWorkedOn = arrayOf(
            Entry("QoL for many existing features"),
            Entry("Updating old code, fixing bugs"),
            Entry("Converting Java to Kotlin (Faster development, less buggy)"),
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

            header("Spare some currency?")
            add(htmlLabel("Software development is time consuming. An occasional dollar adds up when everyone does it.").fitParentWidth())
            strut(4)
            // Progress Bar
            val patreonProgressBar = JProgressBar()
            patreonProgressBar.preferredSize = Dimension(0, 10)
            patreonProgressBar.value = 8
            patreonProgressBar.maximum = 500
            addCenter(JLabel("Patreon Goal: 8 / 500").bold())
            val insetX = 20
            val insetY = 2
            addWithInset(patreonProgressBar, Insets(insetY, insetX, insetY, insetX))
            add(patreonProgressBar)
            strut(4)
            add(JButton("Support SlimTrade :)").onClick { FrameManager.optionsWindow.showDonationPanel() })
            strut()

            /** Road to 1.0.0. */
            header("The road to 1.0.0...")
            add(
                HTMLLabel(
                    "SlimTrade feels close to complete as a trading tool... so what now? " +
                            "I'm thinking about refocusing the project to be more of a general purpose overlay tool. " +
                            "<b>This would include a new name, new features, UI rework, and a website.<b>"
                ).fitParentWidth()
            )
            strutSmall()
            addCenter(JLabel("Thoughts? I want your feedback!").bold().textColor(ThemeColorBlind.GREEN))
            strutSmall()

            /** Take Poll */
            add(JButton("Take Poll (8 Questions)"))
            strut()

            /** Minor Features */
            header("Improvement Ideas")
            label("These are ideas, but will likely happen.").textColor(ThemeColorBlind.YELLOW)
            addEntryGroup(improvementIdeas)
            strut()

            /** New Features */
            header("New Ideas")
            label("These are just ideas, not guarantees.").textColor(ThemeColorBlind.RED)
            addEntryGroup(newFeatureIdeas)
            strut()


            header("Being Worked On")
            addEntryGroup(beingWorkedOn)
            label("This may sound boring (it is), but allows for better future updates.").bold()
                .textColor(ThemeColorBlind.YELLOW)

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

    /** A class for displaying an entry, with possible subentries. */
    class Entry(val text: String) {

        val subentries = ArrayList<String>()

        fun sub(text: String): Entry {
            subentries.add(text)
            return this
        }

    }

}
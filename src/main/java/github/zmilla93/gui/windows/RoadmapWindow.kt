package github.zmilla93.gui.windows

import github.zmilla93.core.References
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.*
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import github.zmilla93.modules.zswing.extensions.PanelExtensions.fitParentWidth
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import github.zmilla93.modules.zswing.theme.ThemeColorBlind
import io.github.zmilla93.modules.theme.UIProperty.FontColorExtensions.textColor
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.*
import javax.swing.event.HyperlinkEvent

class RoadmapWindow : CustomDialog("Roadmap") {

    init {
        val roadmapPanel = RoadmapPanel()
        // Content Panel
        contentPanel.layout = BorderLayout()

        val patchNotesButton = JButton("Patch Notes").onClick { FrameManager.patchNotesWindow.isVisible = true }
        val supportButton = JButton("Support SlimTrade :)").onClick { FrameManager.optionsWindow.showDonationPanel() }

        val southPanel = JPanel(GridBagLayout())
        val gc = ZUtil.getGC()
        val inset = 2
        gc.insets = Insets(inset, inset, inset, inset)
        gc.fill = GridBagConstraints.BOTH
        gc.weightx = 1.0

        southPanel.add(patchNotesButton, gc)
//        gc.gridy++
//        gc.insets.top = 0
        // FIXME : Top/Bottom margin appear slighly off, so subtracting one
        //  Probably an issue with RoundBorder that needs fixing.
//        gc.insets.bottom = insetY - 1
        gc.gridx++
        gc.insets.left = 0
        southPanel.add(supportButton, gc)

        val wrapper = JPanel(BorderLayout())
        wrapper.add(roadmapPanel, BorderLayout.NORTH)
        contentPanel.add(HeaderPanel(), BorderLayout.NORTH)
        contentPanel.add(CustomScrollPane(wrapper), BorderLayout.CENTER)
        contentPanel.add(southPanel, BorderLayout.SOUTH)
        pack()
        size = Dimension(600, 800)
        setLocationRelativeTo(null)
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
            Entry("Menubar customization"),
            Entry("Window system rework"),
            Entry("Discord Revamp"),
            Entry("UI improvements and cleanup"),
            Entry("Dozens of small QOL, UI improvements, and bug fixes"),
        )

        val newFeatureIdeas = arrayOf(
            Entry("Rebrand with a new name and new focus on 'tool overlay' rather than just trading"),
            Entry("Mapping speedrun timer"),
            Entry("Filter Manager - Drag & drop new filters, delete old filters, auto unzip"),
            Entry("URL Bookmarks"),
            Entry("Wiki Helper - Search common item names, open their wiki page"),
//            Entry("League specific tools"),
//            Entry("Integration with other tools"),
//            Entry("Feature Toggle - Hide unused features to free UI space"),
            Entry("Customizable Alert System")
                .sub("Trigger for zone change, level up, npc/boss dialog")
                .sub("Per character tracking for level progression"),
//            Entry("UI Translations"),
            Entry("Betrayal cheat sheet maker (likely web based)"),
            Entry("Website (feature spotlight, downloads, FAQ, guides)"),
            Entry("Web API for dynamic info"),
            Entry("POE API integration"),
            Entry("Native App (.exe, .dbn)")
                .sub("Installing Java not required")
                .sub("Run as admin"),
            Entry("... and more?"),
        )

        val beingWorkedOn = arrayOf(
            Entry("Converting Java to Kotlin (Faster development, less buggy)"),
            Entry("Updating old code"),
            Entry("Fixing bugs"),
        )

        init {
            headerSize = 20
            strutSize = 20
            leftInset = 10

            /** Patreon blurb */
            // FIXME @important: Link to donater page, not patreon directly
            val htmlText = HTMLTextArea(
                "<html>Software development is time consuming, and a man's gotta eat.<br>" +
                        "<a href='${References.PATREON_URL}'>Consider supporting me!</a> An occasional dollar adds up when everyone does it.<br></html>"
            )
            // FIXME : hyperlink class or extension
            htmlText.addHyperlinkListener { e: HyperlinkEvent ->
                if (e.eventType == HyperlinkEvent.EventType.ACTIVATED) {
                    ZUtil.openLink(e.url.toString())
                }
            }
            header("Spare some currency?")
            add(htmlText.fitParentWidth())
            strut(8)
            val progressBar = JProgressBar()
            progressBar.preferredSize = Dimension(0, 10)
            progressBar.value = 8
            progressBar.maximum = 500
            addCenter(JLabel("Current Patreon Goal: 8 / 500").bold())
            val insetX = 20
            val insetY = 2
            addWithInset(progressBar, Insets(insetY, insetX, insetY, insetX))
            add(progressBar)
            strut()

            /** Road to 1.0.0. */
            header("The road to 1.0.0...")
//            label("SlimTrade feels close to complete as a Trading App, so what now...")
            add(
                HTMLLabel(
                    "<html>SlimTrade feels close to complete as a <i>trading tool</i>. " +
                            "I'm thinking of refocusing the project to be '<i>chat parser and tool overlay</i>'. " +
                            "<b>This would include a new name, new features, UI rework, and a website.<b></html>"
                ).fitParentWidth()
            )
            // FIXME : Switch to ComponentPanel of labels to allow option of no-wrap?
//            add(HTMLTextArea("Nothing? Improvements? <i>New features?</i> <i><b>A full rebrand?!<b></i>").fitParentWidth())
            add(JLabel("Thoughts on this idea? I want your feedback!").bold().textColor(ThemeColorBlind.GREEN))
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
            label("This may sound boring (it is), but it's important groundwork for future updates.").bold()
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

    class LimitPanel(panel: JComponent) : JPanel() {

        init {
            layout = BorderLayout()
            add(panel, BorderLayout.CENTER)
            addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent) {
                    preferredSize = null
                    preferredSize = Dimension(0, preferredSize.height)
                }
            })
        }

    }

    class Entry(val text: String) {

        val subentries = ArrayList<String>()

        fun sub(text: String): Entry {
            subentries.add(text)
            return this
        }

    }


}
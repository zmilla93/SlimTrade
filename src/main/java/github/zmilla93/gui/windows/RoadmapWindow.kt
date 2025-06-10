package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.gui.components.*
import github.zmilla93.modules.zswing.extensions.PanelExtensions.fitParentWidth
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import github.zmilla93.modules.zswing.extensions.StyleExtensions.border
import github.zmilla93.modules.zswing.theme.ThemeColorBlind
import io.github.zmilla93.modules.theme.UIProperty.FontColorExtensions.textColor
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Insets
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.*

class RoadmapWindow : CustomDialog("Roadmap") {

    init {
        val roadmapPanel = RoadmapPanel()
        // Content Panel
        contentPanel.layout = BorderLayout()

        val boxPanel = BoxPanel()
        boxPanel.add(HTMLLabel("Here's some long HTML!!! Check it out it is just a bunch of text!!!!!!!!! It just goes and goes and goes!"))
        boxPanel.add(HTMLLabel("Here's some long HTML!!! Check it out it is just a bunch of text!!!!!!!!! It just goes and goes and goes! It just goes and goes and goes! It just goes and goes and goes! It just goes and goes and goes! It just goes and goes and goes! It just goes and goes and goes! It just goes and goes and goes!"))
        boxPanel.add(HTMLLabel("Here's some long HTML!!! Check it out it is just a bunch of text!!!!!!!!! It just goes and goes and goes!"))
        boxPanel.add(HTMLLabel("Here's some long HTML!!! Check it out it is just a bunch of text!!!!!!!!! It just goes and goes and goes!"))

//        val mainPanel = JPanel(BorderLayout())
//        mainPanel.add(CustomScrollPane(roadmapPanel), BorderLayout.NORTH)

        val northPanel = JPanel(BorderLayout())
//        northPanel.add(HeaderPanel(), BorderLayout.NORTH)
//        northPanel.add(CustomScrollPane(roadmapPanel), BorderLayout.CENTER)

        contentPanel.add(HeaderPanel(), BorderLayout.NORTH)
        val wrapper = JPanel(BorderLayout())
        wrapper.add(roadmapPanel, BorderLayout.NORTH)
        contentPanel.add(CustomScrollPane(wrapper).border(null), BorderLayout.CENTER)
        pack()
        size = Dimension(600, 800)
        setLocationRelativeTo(null)
        POEWindow.centerWindow(this)
//        roadmapPanel.limit()
    }

    class HeaderPanel : BoxPanel() {
        init {
            val gap = 10
            strut(gap)
//            val combedLabel = JLabel("SlimTrade Roadmap").fontSize(30).bold()
//            combedLabel.icon = ImageIcon(ImageUtil.scaledImage("/league/sota/POELogo.png", 20))
            val headerIconLabel = JLabel()
            val headerTextLabel = HTMLLabel("<span style='font-size:30px;'><b>SlimTrade Roadmap</b></span>")
            headerIconLabel.icon = ImageIcon(ImageUtil.scaledImage("/league/sota/POELogo.png", 20))
            addCenter(ComponentPanel(headerIconLabel, headerTextLabel))
            strut(gap)
        }
    }

    class RoadmapPanel : BoxPanel() {

        val t = "&nbsp;&nbsp;"

        val t2 = "&#9;"

        val minorFeatures = "- Currency stack conversion, ie \"173c [8s+13]\"<br>" +
                "- Menubar customization<br>" +
                "- Improve macro system - Switch chat channel, custom phrases<br>" +
                "- Improve cheat sheets - Borderless, resizable, transparency<br>" +
                "- URL Bookmarks - Save useful links, optionally give them a hotkey or add them to the menubar<br>" +
                "- Dozens of small QOL, UI improvements, and bug fixes"

        val majorFeatures = "" +
                "- Customizable alert system<br>" +
                "$t- Triggers like zone change, level ups, per character tracking (ie leveling )<br>" +
                "$t- Map speedrunning timer<br>"

//        val wrapPanel = LimitPanel()

        init {
            strutSize = 20

            /** Patreon blurb */
            val htmlText = HTMLTextArea(
                "<html>Software development is time consuming, and a man's gotta eat.<br>" +
                        "<a href=''>Consider supporting me!</a> An occasional dollar really adds up when everyone does it.<br></html>"
            )
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
            add(HTMLTextArea("SlimTrade feels close to complete as a Trading App, so what now...").fitParentWidth())
            add(HTMLTextArea("Nothing? Feature reworks? <i>New Tools?</i> <i><b>A full rebrand?!<b></i>").fitParentWidth())
            label("These are ideas, not guarantees. Results depend on feedback!").bold().textColor(ThemeColorBlind.RED)
            strut()

            /** Minor Features */
            header("Minor Feature Ideas")
            add(HTMLTextArea(minorFeatures).fitParentWidth())
            strut()

            /** Major Features */
            header("Major Feature Ideas")
            add(HTMLTextArea(majorFeatures).fitParentWidth())

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


        // This gets called as the window resizes
//        override fun setPreferredSize(pref: Dimension?) {
//            println("SET PREF SIZE($pref)")
//            super.setPreferredSize(pref)
//            if (parent == null) return
//            if (pref != null) {
//                if (parent.width < pref.width) {
////                    pref.width = parent.width
//                }
////                if (parent.height < pref.height) pref.height = parent.height
//            }
//            super.setPreferredSize(pref)
//        }


//        fun limit() {
//            val widthBuffer = 50
//            preferredSize = null
//            val prefHeight = preferredSize.height
//            println("Pref height: $prefHeight")
//            preferredSize = Dimension(0, prefHeight)
//            val root = SwingUtilities.getWindowAncestor(this) as JDialog
//            val parent = root
//            val pref = preferredSize
//            val widthLimit = parent.width - widthBuffer
//
//            if (pref != null) {
//                if (pref.width > widthLimit) {
//                    println("limit?")
//                    pref.width = widthLimit
//                }
////                if (pref.height > heightLimit) pref.height = heightLimit
//            }
////            preferredSize = pref
//        }
    }


}
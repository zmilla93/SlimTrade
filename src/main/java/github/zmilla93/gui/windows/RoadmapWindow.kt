package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.gui.components.BoxPanel
import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.gui.components.HTMLLabel
import github.zmilla93.gui.components.HTMLTextArea
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.BorderLayout
import java.awt.Color
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

        val mainPanel = JPanel(BorderLayout())
        mainPanel.add(CustomScrollPane(roadmapPanel), BorderLayout.NORTH)

        contentPanel.add(HeaderPanel(), BorderLayout.NORTH)
        contentPanel.add(CustomScrollPane(roadmapPanel), BorderLayout.CENTER)
        pack()
        size = Dimension(600, 800)
        setLocationRelativeTo(null)
        POEWindow.centerWindow(this)
        roadmapPanel.limit()
    }

    class HeaderPanel : BoxPanel() {
        init {
            /** Header */
            strut(10)
            val headerLabel = JLabel("SlimTrade Roadmap").fontSize(30).bold()
            headerLabel.icon = ImageIcon(ImageUtil.scaledImage("/league/sota/POELogo.png", 25))
//            addCenter(JLabel(ImageIcon(image)))
            addCenter(headerLabel)
//            addCenter(ComponentPanel(JLabel("SlimTrade Roadmap").fontSize(30).bold()))
            strut()
        }
    }

    class RoadmapPanel : BoxPanel() {

        val t = "&nbsp;&nbsp;"

        val wrapPanel = LimitPanel()

        init {
            strutSize = 20

            addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    println("Parent resized to: ${size.width} x ${size.height}")
                    limit()
//                    revalidate()
                }
            })

            /** Header */
//            strut(10)
//            val headerLabel = JLabel("SlimTrade Roadmap").fontSize(30).bold()
//            headerLabel.icon = ImageIcon(ImageUtil.scaledImage("/league/sota/POELogo.png", 25))
////            addCenter(JLabel(ImageIcon(image)))
//            addCenter(headerLabel)
////            addCenter(ComponentPanel(JLabel("SlimTrade Roadmap").fontSize(30).bold()))
//            strut()

            /** Patreon blurb */
            val htmlText = HTMLTextArea(
                "<html>Development is time consuming, and a man's gotta eat.<br>" +
                        "<a href=''>Consider supporting me, especially on Patreon!</a> An occasional dollar really adds up when everyone does it. :)<br></html>"
            )

            wrapPanel.layout = BorderLayout()
            wrapPanel.background = Color.RED
//            wrapPanel.add(htmlText, BorderLayout.CENTER)
            wrapPanel.add(htmlText, BorderLayout.CENTER)
//            wrapPanel.preferredSize = Dimension(1200, 50)
//            wrapPanel.maximumSize = Dimension(400, 9999)
//            wrapPanel.preferredSize = Dimension(400, -1)
            header("Spare some currency?")
            add(wrapPanel)
            strut()
            val progressBar = JProgressBar()
            progressBar.maximum = 500
            progressBar.value = 8
            addCenter(JLabel("Current Patreon Goal: 8 / 500").bold())
            val insetX = 20
            val insetY = 2
            addWithInset(progressBar, Insets(insetY, insetX, insetY, insetX))
            strut()

            /** Road to 1.0.0. */
            header("The road to 1.0.0...")
            htmlLabel("SlimTrade is feel... Nothing? More Tools? UI rework? <i>A full rebrand?!</i>")
            label("These are ideas and not promises. Results depend on feedback.").bold()
            strut()

            /** Minor Features */
            header("Minor Feature Ideas")
//            add(HTMLTextArea(minorFeatures))
            strut()

            /** Major Features */
            header("Major Feature Ideas")
//            add(HTMLTextArea(majorFeatures))

        }

        fun limit() {
            wrapPanel.limit()
        }

    }

    class LimitPanel : JPanel() {

        init {
            background = Color.CYAN
        }

        override fun getMaximumSize(): Dimension? {
            print("SIZE: ${parent.size}")
            return parent.size
        }

        override fun getSize(): Dimension? {
            println("GET SIZE")
            return super.getSize()
        }

        override fun setPreferredSize(pref: Dimension?) {
            super.setPreferredSize(pref)
            println("SET PREF")
            if (parent == null) {
                println("NULL PARENT")
                return
            }
            if (pref != null) {
                if (parent.width < pref.width) pref.width = parent.width
//                if (parent.height < pref.height) pref.height = parent.height
            }
            println("modpref: " + pref)
            super.setPreferredSize(pref)
        }

        fun limit() {
            val widthBuffer = 50
            preferredSize = null
            val prefHeight = preferredSize.height
            println("Pref height: $prefHeight")
            preferredSize = Dimension(40, prefHeight)
            val root = SwingUtilities.getWindowAncestor(this) as JDialog
            val parent = root
            val pref = preferredSize
            println("pref:" + pref)
            val widthLimit = parent.width - widthBuffer
            // FIXME : Height limit
            val heightLimit = 9999
            if (pref != null) {
                if (pref.width > widthLimit) pref.width = widthLimit
//                if (pref.height > heightLimit) pref.height = heightLimit
            }
//            pref.width = 40
            println("pref2:" + pref)
            this.preferredSize = pref
        }
    }


}
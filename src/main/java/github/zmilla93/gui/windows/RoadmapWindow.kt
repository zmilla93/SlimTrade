package github.zmilla93.gui.windows

import github.zmilla93.gui.components.BoxPanel
import github.zmilla93.gui.components.HTMLTextArea
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JProgressBar

class RoadmapWindow : CustomDialog("Roadmap") {

    init {
        // Content Panel
        contentPanel.background = Color.RED
        contentPanel.layout = BorderLayout()
        contentPanel.add(RoadmapPanel(), BorderLayout.NORTH)
        pack()
        size = Dimension(600, 800)
        setLocationRelativeTo(null)
    }

    class RoadmapPanel : BoxPanel() {

        val t = "&nbsp;&nbsp;"

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

        init {
            strutSize = 26

            /** Patreon blurb */
            header("Spare some currency?", 14)
            add(
                HTMLTextArea(
                    "SlimTrade takes a lot of time to develop, and a man's gotta eat.<br>" +
                            "<a href=''>Consider supporting me, especially on Patreon!</a> An occasional $1 really adds up when a lot of people do it. :)<br>"
                )
            )
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
            htmlLabel("SlimTrade is in a decent state, so what now... Nothing? More Tools? UI rework? <i>A full rebrand?!</i>")
            label("These are ideas and not promises. Results depend on feedback.").bold()
            strut()

            /** Minor Features */
            header("Minor Feature Ideas")
            add(HTMLTextArea(minorFeatures))
            strut()

            /** Major Features */
            header("Major Feature Ideas")
            add(HTMLTextArea(majorFeatures))
        }

    }


}
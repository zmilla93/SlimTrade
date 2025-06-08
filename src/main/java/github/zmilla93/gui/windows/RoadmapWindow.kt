package github.zmilla93.gui.windows

import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.gui.components.HTMLTextArea
import github.zmilla93.gui.options.AbstractOptionPanel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagConstraints

class RoadmapWindow : CustomDialog("Roadmap") {

    val panel = AbstractOptionPanel()

    init {

        panel.addHeader("Spare some currency?")
        panel.addFullWidthComponent(
            HTMLTextArea(
                "SlimTrade takes a lot of time to develop, and a man's gotta eat.<br>" +
                        "Consider supporting on <a href=''>Patreon</a>. An occasional $1 can really add up with thousands of users."
            )
        )
//        panel.addFullWidthComponent(JLabel("The road to 1.0.0..."))
        panel.addVerticalStrut()
        panel.addHeader("The road to 1.0.0...")
        panel.addFullWidthComponent(HTMLTextArea("Here's some things I'd like to do. Not all features are promised, and depend on interest/support."))

        contentPanel.layout = BorderLayout()
//        contentPanel.layout = GridBagLayout()
        val gc = GridBagConstraints()
        gc.weightx = 1.0
        gc.weighty = 1.0
        gc.fill = GridBagConstraints.BOTH
        contentPanel.background = Color.RED
//        contentPanel.add(panel, gc)
//        contentPanel.add(panel, BorderLayout.CENTER)
        contentPanel.add(
            CustomScrollPane(
                HTMLTextArea(
                    "SlimTrade takes a lot of time to develop, and a man's gotta eat.<br>" +
                            "Consider supporting on <a href=''>Patreon</a>! An occasional $1 can really add up with thousands of users."
                )
            ), BorderLayout.CENTER
        )
        pack()
        size = Dimension(600, 800)
    }


}
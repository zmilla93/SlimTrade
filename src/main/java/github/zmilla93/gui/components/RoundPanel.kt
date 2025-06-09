package github.zmilla93.gui.components

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel


class RoundPanel : JPanel() {

    var arc = RoundBorder.DEFAULT_ARC
        set(value) {
            field = value
            repaint()
        }

    override fun paintComponent(g: Graphics) {
        val g2 = g as Graphics2D
        g2.color = background
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.fillRoundRect(0, 0, width, height, arc, arc)
    }

}
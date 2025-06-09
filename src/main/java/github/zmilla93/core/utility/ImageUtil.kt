package github.zmilla93.core.utility

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel

object ImageUtil {

    fun resourceImageLabel(path: String, width: Int? = null, height: Int? = null): JLabel {
        return JLabel(resourceIcon(path, width, height))
    }

    fun resourceIcon(path: String, width: Int? = null, height: Int? = null): ImageIcon {
        return ImageIcon(resourceImage(path, width, height))
    }

    fun resourceImage(path: String, width: Int? = null, height: Int? = null): Image {
        val image = ImageIO.read(ZUtil.javaClass.getResourceAsStream(path))
        val finalHeight = height ?: width
        if (width != null && finalHeight != null)
            return image.getScaledInstance(width, finalHeight, Image.SCALE_SMOOTH)
        return image
    }

    fun scaledImage(path: String, scale: Int): Image {
        val image = ImageIO.read(ZUtil.javaClass.getResourceAsStream(path))
        if (scale > 0) {
            val width = (image.width / 100f * scale).toInt()
            val height = (image.height / 100f * scale).toInt()
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH)
        }
        return image
    }

}
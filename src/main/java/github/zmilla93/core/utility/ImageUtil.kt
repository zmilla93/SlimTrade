package github.zmilla93.core.utility

import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * Class for interacting with images.
 * If you are working UI icons, use [IconUtil] instead for recoloring and better caching.
 */
object ImageUtil {

//    fun resourceImageLabel(path: String, width: Int? = null, height: Int? = null): JLabel {
//        return JLabel(resourceIcon(path, width, height))
//    }
//
//    fun resourceIcon(path: String, width: Int? = null, height: Int? = null): ImageIcon {
//        return ImageIcon(resourceImage(path, width, height))
//    }

    fun resourceImage(path: String, width: Int? = null, height: Int? = null): Image {
        val image = ImageIO.read(ZUtil.javaClass.getResourceAsStream(path))
        val finalHeight = height ?: width
        if (width != null && finalHeight != null)
            return image.getScaledInstance(width, finalHeight, Image.SCALE_SMOOTH)
        return image
    }

    fun scaledImage(path: String, scale: Int): Image {
        val image: BufferedImage
        try {
            image = ImageIO.read(ZUtil.javaClass.getResourceAsStream(path))
        } catch (e: IllegalArgumentException) {
            throw RuntimeException("Image not found: $path")
        }
        if (scale > 0) {
            val width = (image.width / 100f * scale).toInt()
            val height = (image.height / 100f * scale).toInt()
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH)
        }
        return image
    }

}
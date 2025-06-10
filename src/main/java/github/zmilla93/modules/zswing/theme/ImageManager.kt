package github.zmilla93.modules.zswing.theme

import github.zmilla93.modules.theme.ThemeManager
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.nio.file.Path
import javax.imageio.ImageIO

object ImageManager {

    private val logger = LoggerFactory.getLogger(ImageManager.javaClass)

    /** Reads an image from the resource folder. */
    fun readImageResource(path: String?): BufferedImage? {
        if (path == null) return null
        var target: String = path
        // Ensure "/" prefix
        if (!target.startsWith("/")) target = "/$target"
        val resource = IconManager::class.java.getResource(target)
        if (resource == null) throw RuntimeException(
            "Image resource '" + target + "' not found. " +
                    "If this resource exists and the error continues, try cleaning & recompiling the project."
        )
//        logger.info("Loaded image '$path'")
        return ImageIO.read(resource)
    }

    fun readImageFromDisk(path: String?) {

    }

    fun readImageFromDisk(path: Path?) {

    }

    // FIXME : name?
    fun requiresScaling(img: BufferedImage, targetSize: Int): Boolean {
        return img.width != targetSize && img.height != targetSize
    }

    /** Scales an image while maintaining aspect ratio. */
    fun scaleImage(img: BufferedImage, targetSize: Int): Image {
        if (!requiresScaling(img, targetSize)) return img
        var width = targetSize
        var height = targetSize
        // Scale non square images
        if (img.width > img.height) {
            val mult = targetSize.toFloat() / img.height
            width = Math.round(img.width * mult)
        } else {
            val mult = targetSize.toFloat() / img.width
            height = Math.round(img.height * mult)
        }
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH)
    }

    fun recolorBufferedImage(image: BufferedImage, color: Color): BufferedImage {
        val width = image.width
        val height = image.height
        val raster = image.raster
        val pixel = IntArray(4)
        for (x in 0..<width) {
            for (y in 0..<height) {
                raster.getPixel(x, y, pixel)
                pixel[0] = color.red
                pixel[1] = color.green
                pixel[2] = color.blue
                raster.setPixel(x, y, pixel)
            }
        }
        return image
    }

    fun BufferedImage.recolorImage(color: Color = ThemeManager.textColor): BufferedImage {
        return recolorBufferedImage(this, color)
    }

}
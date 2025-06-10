//package github.zmilla93.modules.zswing.theme
//
//import java.awt.Color
//import java.awt.Image
//import java.awt.image.BufferedImage
//import javax.imageio.ImageIO
//
///**
// * Class for working with UI icons.
// * Handles recoloring and caching.
// */
//object IconUtil {
//
//    fun readImageResource(path: String): BufferedImage? {
////        if (path == null) return null
//        var target = path
//        // Ensure "/" prefix
//        if (!target.startsWith("/")) target = "/$target"
//        val resource = IconUtil::class.java.getResource(target)
//        if (resource == null) throw RuntimeException(
//            "Image resource '" + target + "' not found. " +
//                    "If this resource exists and the error continues, try cleaning & recompiling the project."
//        )
//        return ImageIO.read(resource)
//    }
//
//
//    fun requiresScaling(img: BufferedImage, targetSize: Int): Boolean {
//        return img.width != targetSize && img.height != targetSize
//    }
//
//    /** Scales an image while maintaining aspect ratio. */
//    fun scaleImage(img: BufferedImage, targetSize: Int): Image {
//        if (!requiresScaling(img, targetSize)) return img
//        var width = targetSize
//        var height = targetSize
//        // Scale non square images
//        if (img.width > img.height) {
//            val mult = targetSize.toFloat() / img.height
//            width = Math.round(img.width * mult)
//        } else {
//            val mult = targetSize.toFloat() / img.width
//            height = Math.round(img.height * mult)
//        }
//        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH)
//    }
//
//    fun recolorBufferedImage(image: BufferedImage, color: Color): BufferedImage {
//        val width = image.width
//        val height = image.height
//        val raster = image.raster
//        val pixel = IntArray(4)
//        for (x in 0..<width) {
//            for (y in 0..<height) {
//                raster.getPixel(x, y, pixel)
//                pixel[0] = color.red
//                pixel[1] = color.green
//                pixel[2] = color.blue
//                raster.setPixel(x, y, pixel)
//            }
//        }
//        return image
//    }
//
//}
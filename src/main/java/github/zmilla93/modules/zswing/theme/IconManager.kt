package github.zmilla93.modules.zswing.theme

import github.zmilla93.modules.theme.ThemeManager
import github.zmilla93.modules.zswing.theme.ImageManager.recolorImage
import org.slf4j.LoggerFactory
import java.awt.Color
import javax.swing.ImageIcon

object IconManager {

    val defaultIconSize = 16
    private val iconCache = HashMap<CachedIcon, ImageIcon>()
    private val colorIconCache = HashMap<CachedIcon, ImageIcon>()

    private val logger = LoggerFactory.getLogger(IconManager.javaClass)

    // TODO : Call on theme change (themeListener?)
    fun clearCache() {
        iconCache.clear()
        colorIconCache.clear()
    }

    // FIXME @important : resource folder is wrong
    fun getIcon(path: String, sizeOr0: Int = 0): ImageIcon {
//        if (path == null) return null
        val targetSize = if (sizeOr0 <= 0) defaultIconSize else sizeOr0
        assert(targetSize > 0)
        val cachedIcon = CachedIcon(path, targetSize)
        // Return cached image if possible
        if (iconCache.containsKey(cachedIcon)) return iconCache[cachedIcon]!!
        // FIXME : always resource folder
        val imgBuf = ImageManager.readImageResource(path)
        if (imgBuf == null) throw RuntimeException("Resource not found: $path")
        val img = ImageManager.scaleImage(imgBuf, targetSize)
        val icon = ImageIcon(img)
        // Cache Image
        iconCache[cachedIcon] = icon
        return icon
    }

    fun getColorIcon(path: String?, sizeOr0: Int = 0, color: Color = ThemeManager.textColor): ImageIcon? {
        if (path == null) return null
        val targetSize = if (sizeOr0 <= 0) defaultIconSize else sizeOr0
//        val targetColor = colorOrNull ?: UIColor.LABEL_FOREGROUND.color()
        assert(targetSize > 0)
        val cachedIcon = CachedIcon(path, targetSize, color)
        // Return cached image if possible
        if (colorIconCache.containsKey(cachedIcon)) return colorIconCache[cachedIcon]
        // FIXME : always resource folder
        // Create Image
        val imgBuf = ImageManager.readImageResource(path) ?: return null
        imgBuf.recolorImage(color)
        val img = ImageManager.scaleImage(imgBuf, targetSize)
        val icon = ImageIcon(img)
        // Cache Image
//        logger.info("Recolored image '$cachedIcon'")
        colorIconCache[cachedIcon] = icon
        return icon
    }

    private fun createIcon() {

    }


}
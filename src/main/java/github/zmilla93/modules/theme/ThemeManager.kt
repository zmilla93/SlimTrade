package github.zmilla93.modules.theme

import com.formdev.flatlaf.icons.FlatCheckBoxIcon
import github.zmilla93.core.managers.FontManager
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.buttons.IconButton
import github.zmilla93.gui.windows.BasicDialog
import github.zmilla93.modules.stopwatch.Stopwatch
import github.zmilla93.modules.theme.ThemeManager.frames
import github.zmilla93.modules.theme.components.ColorCheckbox
import github.zmilla93.modules.theme.extensions.ThemeExtension
import github.zmilla93.modules.theme.listeners.ColorblindChangeListener
import github.zmilla93.modules.theme.listeners.IDetailedFontChangeListener
import github.zmilla93.modules.theme.listeners.IFontChangeListener
import github.zmilla93.modules.theme.listeners.IThemeListener
import github.zmilla93.modules.updater.ZLogger
import io.github.zmilla93.modules.theme.UIProperty
import java.awt.*
import java.awt.datatransfer.StringSelection
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.plaf.BorderUIResource
import javax.swing.plaf.FontUIResource
import javax.swing.plaf.IconUIResource

object ThemeManager {
    // FIXME : Should this be moved to frame manager?
    val frames: MutableList<Component> = ArrayList<Component>()

    // Listeners
    private val themeListeners = ArrayList<IThemeListener>()
    private val stickyCombos = ArrayList<JComboBox<*>?>()
    private val fontChangeListeners = ArrayList<IFontChangeListener>()
    private val detailedFontChangeListeners = ArrayList<IDetailedFontChangeListener>()
    private val colorblindChangeListeners = ArrayList<ColorblindChangeListener>()

    var cachedIconSize: Int = 18
        private set
    private var currentFontSize = 0
    private val iconMap = HashMap<String?, ImageIcon?>()
    private val colorIconMap = HashMap<String?, ImageIcon?>()

    @JvmField
    val TRANSPARENT: Color = Color(0, 0, 0, 0)

    @JvmField
    val TRANSPARENT_CLICKABLE: Color = Color(0, 0, 0, 1)

    @JvmField
    val POE_TEXT_DARK: Color = Color(53, 28, 13)

    @JvmField
    val POE_TEXT_LIGHT: Color = Color(254, 192, 118)
    private const val DEFAULT_OFFSET_COLOR_AMOUNT = 20

    private var currentTheme: Theme? = null
    private var currentFontName: String? = null

    @JvmStatic
    var isColorblindMode: Boolean = false
        set(state) {
            if (state == field) return
            field = state
            UIManager.setLookAndFeel(currentTheme!!.lookAndFeel.javaClass.newInstance())
            updateAllComponentTrees()
            for (listener in colorblindChangeListeners) listener.onColorblindChange(state)
        }

    // Flags that monitor changes to font
    private var iconSizeWasChanged = false
    private var fontSizeWasChanged = false
    private var fontStyleWasChanged = false

    @JvmStatic
    fun addFrame(frame: Component?) {
        if (frames.contains(frame)) return
        frames.add(frame!!)
    }

    @JvmStatic
    fun removeFrame(frame: Component?) {
        frames.remove(frame)
    }

    // Sticky combos are JComboBoxes that contain colored icons.
    // These combos need to have their selected values manually updated after switching themes
    @JvmStatic
    fun addStickyCombo(combo: JComboBox<*>?) {
        stickyCombos.add(combo)
    }

    @JvmStatic
    fun removeStickyCombo(combo: JComboBox<*>?) {
        stickyCombos.remove(combo)
    }

    @JvmStatic
    fun setTheme(theme: Theme?) {
        setTheme(theme, false)
    }

    @JvmStatic
    fun setTheme(theme: Theme?, forceThemeRefresh: Boolean) {
        var theme = theme
        assert(SwingUtilities.isEventDispatchThread())
        if (theme == null) theme = Theme.getDefaultColorTheme()
        if (theme == currentTheme && !forceThemeRefresh) return
        val comboIcons = IntArray(stickyCombos.size)
        for (i in stickyCombos.indices) {
            comboIcons[i] = stickyCombos.get(i)!!.getSelectedIndex()
        }
        iconMap.clear()
        colorIconMap.clear()
        currentTheme = theme
        try {
            UIManager.setLookAndFeel(currentTheme!!.lookAndFeel.javaClass.newInstance())
        } catch (e: UnsupportedLookAndFeelException) {
            throw RuntimeException("Unsupported theme!")
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unsupported theme!")
        } catch (e: InstantiationException) {
            throw RuntimeException("Unsupported theme!")
        }
        patchTheme()
        updateAllComponentTrees()
        for (i in stickyCombos.indices) {
            stickyCombos.get(i)!!.setSelectedIndex(comboIcons[i])
        }
        for (listener in themeListeners) {
            listener.onThemeChange()
        }
    }

    /** Call this anytime [UIProperty]s need to be reevaluated or reapplied. */
    fun updateUIProperties() {
        updateAllComponents {
            // FIXME @important : Check if default font size is enabled
            // Apply default font size
            it.font = it.font.deriveFont(FontManager.fontSize.toFloat())
            for (prop in UIProperty.entries) {
                val value = UIProperty.getProperty<Any>(it, prop)
                if (value != null) {
                    // Apply UI Property
                    prop.runner.applyProperty(it, value)
                }
            }
        }
    }

    /** Runs an update function on every component in [frames] window hierarchy. */
    fun updateAllComponents(updater: ComponentUpdater) {
        for (frame in frames) {
            var root: Container? = null
            if (frame is JFrame) root = frame.contentPane
            else if (frame is JDialog) root = frame.contentPane
            if (root is JComponent) updateComponentRecursive(root, updater)
        }
    }

    /**
     * Runs an updater function [ComponentUpdater] on a component,
     * as well as any child components.
     */
    private fun updateComponentRecursive(comp: JComponent, updater: ComponentUpdater) {
        updater.update(comp)
        comp.components.forEach { if (it is JComponent) updateComponentRecursive(it, updater) }
    }

    fun setThemeFastDebug(theme: Theme?) {
        setThemeFastDebug(theme, true)
    }

    @JvmStatic
    fun setThemeFastDebug(theme: Theme?, rebuildIcons: Boolean) {
        currentTheme = theme
        if (rebuildIcons) colorIconMap.clear()
        try {
            UIManager.setLookAndFeel(currentTheme!!.lookAndFeel)
        } catch (e: UnsupportedLookAndFeelException) {
            throw RuntimeException("Unsupported theme!")
        }
    }

    private fun updateAllComponentTrees() {
        for (frame in frames) SwingUtilities.updateComponentTreeUI(frame)
    }


    @JvmStatic
    fun getCurrentTheme(): Theme {
        if (currentTheme == null) return Theme.getDefaultColorTheme()
        return currentTheme!!
    }

    @JvmStatic
    fun extensions(): ThemeExtension? {
        return getCurrentTheme().extensions
    }

    // FIXME: Review this
    // Apply custom patching for theme issues. This is a bit hacky, but should be good enough until the themes are updated.
    private fun patchTheme() {
        // If the text areas and panels have the same background, add a border to the text area.
        val panelBackground = UIManager.getColor("Panel.background")
        val textAreaBackground = UIManager.getColor("TextArea.background")
        if (panelBackground == textAreaBackground) {
            val buttonBorderColor = UIManager.getColor("Button.borderColor")
            UIManager.put("TextArea.border", BorderUIResource(BorderFactory.createLineBorder(buttonBorderColor)))
        } else {
            UIManager.put("TextArea.border", BorderUIResource(BorderFactory.createEmptyBorder()))
        }

        // Solarized Light Fix - Make checkboxes color match other inputs instead of being white
        if (currentTheme!!.name == "SOLARIZED_LIGHT") {
            UIManager.put("CheckBox.icon", IconUIResource(ColorCheckbox()))
        } else {
            UIManager.put("CheckBox.icon", IconUIResource(FlatCheckBoxIcon()))
        }
    }

    // When getting an icon, size uses cached size, -1 uses unscaled size
    @JvmStatic
    fun getIcon(path: String): ImageIcon? {
        return getIcon(path, cachedIconSize, true)
    }

    fun getIcon(path: String, resourceFolder: Boolean): ImageIcon? {
        return getIcon(path, cachedIconSize, resourceFolder)
    }

    @JvmStatic
    fun getIcon(path: String, size: Int): ImageIcon? {
        return getIcon(path, size, true)
    }

    @JvmStatic
    fun getIcon(path: String, size: Int, resourceFolder: Boolean): ImageIcon? {
        var size = size
        if (size == 0) size = cachedIconSize
        // Return cached image if possible
        if (size == cachedIconSize && iconMap.containsKey(path)) {
            return iconMap.get(path)
        }
        try {
            val img: BufferedImage?
            if (resourceFolder) img =
                ImageIO.read(Objects.requireNonNull<URL?>(ThemeManager::class.java.getResource(path)))
            else img = ImageIO.read(File(path))
            if (img == null) return null // This will only trigger with user submitted images

            var width = size
            var height = size
            // Scale non square images
            if (img.getWidth() > img.getHeight()) {
                val mult = size.toFloat() / img.getHeight()
                width = Math.round(img.getWidth() * mult)
            } else {
                val mult = size.toFloat() / img.getWidth()
                height = Math.round(img.getHeight() * mult)
            }
            val icon: ImageIcon?
            if (size == -1) {
                icon = ImageIcon(img)
            } else {
                icon = ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH))
            }
            if (size == cachedIconSize) iconMap.put(path, icon)
            return icon
        } catch (e: IOException) {
            ZLogger.err("Could not find image: " + path)
            ZLogger.err(e.getStackTrace())
            return null
        } catch (e: NullPointerException) {
            ZLogger.err("Could not find image: " + path)
            ZLogger.err(e.getStackTrace())
            return null
        }
    }

    @JvmStatic
    fun getColorIcon(path: String): ImageIcon? {
        return getColorIcon(path, cachedIconSize)
    }

    @JvmStatic
    fun getColorIcon(path: String, size: Int): ImageIcon? {
        var size = size
        if (size == 0) size = cachedIconSize
        // Return cached image if possible
        if (size == cachedIconSize && colorIconMap.containsKey(path)) {
            return colorIconMap.get(path)
        }
        // Generate new image
        try {
            val img = ImageIO.read(Objects.requireNonNull<URL?>(ThemeManager::class.java.getResource(path)))
            val icon = ImageIcon(
                getColorImage(img, UIManager.getColor("Button.foreground")).getScaledInstance(
                    size,
                    size,
                    Image.SCALE_SMOOTH
                )
            )
            if (size == cachedIconSize) colorIconMap.put(path, icon)
            return icon
        } catch (e: IOException) {
            ZLogger.err("Invalid path: " + path)
            ZLogger.err(e.getStackTrace())
        } catch (e: NullPointerException) {
            ZLogger.err("Invalid path: " + path)
            ZLogger.err(e.getStackTrace())
        }
        return null
    }

    private fun getColorImage(image: BufferedImage, color: Color): Image {
        val width = image.getWidth()
        val height = image.getHeight()
        val raster = image.getRaster()
        for (xx in 0..<width) {
            for (yy in 0..<height) {
                val pixels = raster.getPixel(xx, yy, null as IntArray?)
                pixels[0] = color.getRed()
                pixels[1] = color.getGreen()
                pixels[2] = color.getBlue()
                raster.setPixel(xx, yy, pixels)
            }
        }
        return image
    }

    @JvmStatic
    fun setFont(fontName: String?) {
        if (fontName == null) return
        if (fontName == currentFontName) return
        fontStyleWasChanged = true
        Stopwatch.start()
        if (fontName == currentFontName) return
        if (!FontManager.isValidFont(fontName)) return
        FontManager.setPreferredFont(fontName)
        refreshDefaultFonts()
        for (component in frames) {
            changeComponentFont(component, FontManager.getPreferredFont())
        }
        currentFontName = fontName
    }

    private fun refreshDefaultFonts() {
        if (FontManager.USE_SYSTEM_DEFAULT) return
        val keys = UIManager.getDefaults().keys()
        val fontName = FontManager.getPreferredFont().getFontName()
        while (keys.hasMoreElements()) {
            val key = keys.nextElement()
            val value = UIManager.get(key)
            if (value is FontUIResource) {
                val oldFont: Font = value
                val fontResource =
                    FontUIResource(fontName, oldFont.getStyle(), SaveManager.settingsSaveFile.data.fontSize)
                UIManager.put(key, fontResource)
            }
        }
    }

    private fun changeComponentFont(component: Component, font: Font) {
        val previousFont = component.getFont()
        component.setFont(font.deriveFont(previousFont.getStyle(), previousFont.getSize().toFloat()))
        if (component is Container) {
            for (child in component.getComponents()) {
                changeComponentFont(child, font)
            }
        }
    }

    @JvmStatic
    fun setFontSize(size: Int) {
        if (size == FontManager.fontSize) return
        return
        FontManager.fontSize = size
        fontSizeWasChanged = true
        // FIXME : listeners?
//        updateUIProperties()

//        refreshDefaultFonts()
//        for (frame in frames) {
//            setFontSizeRecursive(frame, size)
//            //            SwingUtilities.updateComponentTreeUI(frame);
//            frame.revalidate()
//            frame.repaint()
//        }
    }

    private fun setFontSizeRecursive(component: Component, size: Int) {
        if (component is Container) {
            for (child in component.getComponents()) {
                setFontSizeRecursive(child, size)
            }
        }
        val curFont = component.getFont()
        component.setFont(curFont.deriveFont(curFont.getStyle(), size.toFloat()))
    }

    @JvmStatic
    fun setIconSize(size: Int) {
        assert(SwingUtilities.isEventDispatchThread())
        if (size == cachedIconSize) return
        iconSizeWasChanged = true
        cachedIconSize = size
        iconMap.clear()
        colorIconMap.clear()
        for (frame in frames) {
//            setIconSizeRecursive(frame, size);
            // FIXME : updateComponentThread is very inefficient, should create an alternative function
            SwingUtilities.updateComponentTreeUI(frame)
            frame.revalidate()
            frame.repaint()
            // FIXME
            if (frame is BasicDialog) {
                frame.pack()
            }
        }
    }

    private fun setIconSizeRecursive(component: Component?, size: Int) {
        if (component is Container) {
            for (child in component.getComponents()) {
                setIconSizeRecursive(child, size)
            }
        }
        if (component is IconButton) {
            component.setIconSize(size)
        }
    }

    fun recursiveUpdateUI(component: JComponent) {
        component.updateUI()
        for (child in component.getComponents()) {
            ThemeManager.recursiveUpdateUI((child as JComponent?)!!)
        }
    }

    @JvmStatic
    fun checkFontChange() {
        val fontWasChanged = fontStyleWasChanged || fontSizeWasChanged || iconSizeWasChanged
        if (!fontWasChanged) return
        for (listener in fontChangeListeners) listener.onFontChanged()
        if (fontStyleWasChanged) for (listener in detailedFontChangeListeners) listener.onFontStyleChanged()
        if (fontSizeWasChanged) for (listener in detailedFontChangeListeners) listener.onFontSizeChanged()
        if (iconSizeWasChanged) for (listener in detailedFontChangeListeners) listener.onIconSizeChanged()
        fontStyleWasChanged = false
        fontSizeWasChanged = false
        iconSizeWasChanged = false
    }

    //
    // Utility Color Functions
    //
    fun getDarkerColor(colorA: Color, colorB: Color): Color {
        if (colorIntValue(colorA) < colorIntValue(colorB)) return colorA
        return colorB
    }

    @JvmStatic
    fun getLighterColor(colorA: Color, colorB: Color): Color {
        if (colorIntValue(colorA) > colorIntValue(colorB)) return colorA
        return colorB
    }

    fun getBrightness(color: Color): Float {
        return 0.2126f * color.getRed() + 0.7152f * color.getGreen() + 0.0722f * color.getBlue()
    }

    fun isDark(color: Color): Boolean {
        return getBrightness(color) < 128
    }

    fun modify(c: Color, mod: Int): Color {
        val min = 0
        val max = 255
        val r = ZUtil.clamp(c.getRed() + mod, min, max)
        val g = ZUtil.clamp(c.getGreen() + mod, min, max)
        val b = ZUtil.clamp(c.getBlue() + mod, min, max)
        return Color(r, g, b)
    }

    @JvmStatic
    fun lighter(c: Color): Color {
        return modify(c, DEFAULT_OFFSET_COLOR_AMOUNT)
    }

    fun lighter(c: Color, increase: Int): Color {
        return modify(c, increase)
    }

    private fun colorIntValue(color: Color): Int {
        return color.getRed() + color.getGreen() + color.getBlue()
    }

    @JvmStatic
    fun adjustAlpha(color: Color, alpha: Int): Color {
        return Color(color.getRed(), color.getGreen(), color.getBlue(), alpha)
    }

    /**
     * Dumps all the Key, Value pairs from the UIManager into the clipboard.
     */
    @JvmStatic
    fun debugKeyValueDump() {
        val keys = UIManager.getDefaults().keys()
        val builder = StringBuilder()
        while (keys.hasMoreElements()) {
            val key = keys.nextElement()
            val value = UIManager.get(key)
            builder.append(key).append(" ::: ").append(value).append("\n")
        }
        val selection = StringSelection(builder.toString())
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null)
    }

    // Listeners
    @JvmStatic
    fun addThemeListener(listener: IThemeListener?) {
        themeListeners.add(listener!!)
    }

    @JvmStatic
    fun removeThemeListener(listener: IThemeListener?) {
        themeListeners.remove(listener)
    }

    // FIXME : Font listener should be moved to FontManager
    @JvmStatic
    fun addFontListener(listener: IFontChangeListener?) {
        fontChangeListeners.add(listener!!)
    }

    @JvmStatic
    fun removeFontChangeListener(listener: IFontChangeListener?) {
        fontChangeListeners.remove(listener)
    }

    @JvmStatic
    fun addColorblindChangeListener(listener: ColorblindChangeListener?) {
        colorblindChangeListeners.add(listener!!)
    }

    fun removeColorblindChangeListener(listener: ColorblindChangeListener?) {
        colorblindChangeListeners.remove(listener)
    }
}

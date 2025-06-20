package github.zmilla93.core.managers

import github.zmilla93.core.data.FontLanguageSupport
import github.zmilla93.core.enums.FontLanguage
import github.zmilla93.core.language.UnicodeRange
import github.zmilla93.modules.theme.ThemeManager
import github.zmilla93.modules.updater.ZLogger
import java.awt.Font
import java.awt.FontFormatException
import java.awt.GraphicsEnvironment
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.UIManager

/**
 * Handles loading fonts and font selection for foreign languages.
 * Applying the preferred font to the UIManager is handled in the ThemeManager.
 *
 * @see ThemeManager
 */
object FontManager {
    private var loaded = false
    const val USE_SYSTEM_DEFAULT: Boolean = false

    @JvmField
    var DEFAULT_FONT: String = "Veranda"

    const val ENGLISH_EXAMPLE_TEXT: String = "English - Language Test"
    const val CHINESE_EXAMPLE_TEXT: String = "Chinese - 语言测试"
    const val JAPANESE_EXAMPLE_TEXT: String = "Japanese - 語学テスト"
    const val KOREAN_EXAMPLE_TEXT: String = "Korean - 어학시험"
    const val RUSSIAN_EXAMPLE_TEXT: String = "Russian - Языковой тест"
    const val THAI_EXAMPLE_TEXT: String = "Thai - แบบทดสอบภาษา"

    private var systemFont: Font? = null
    private var preferredFont: Font? = null
    private var koreanFont: Font? = null
    private var thaiFont: Font? = null

    private var systemFontSupport: FontLanguageSupport? = null
    private var preferredFontSupport: FontLanguageSupport? = null

    private val validFonts = ArrayList<String>()
    private val fontBlacklist = HashSet<String?>()


    // Font Size
    val MIN_FONT_SIZE = 4
    val MAX_FONT_SIZE = 42
    var fontSize = 12
        set(value) {
            val previous = field
            var clampedValue = value
            if (value < MIN_FONT_SIZE) clampedValue = MIN_FONT_SIZE
            if (value > MAX_FONT_SIZE) clampedValue = MAX_FONT_SIZE
            if (clampedValue != previous) {
//                println("SET FONT SIZE: $target")
                field = value
                ThemeManager.updateUIProperties()
            }
        }
//        get() {
//            println("GETTING: " + field)
//            return field
//        }

    init {
        fontBlacklist.add("Gabriola")
        fontBlacklist.add("Microsoft Himalaya")
        fontBlacklist.add("Microsoft Yi Baiti")
    }

    @JvmStatic
    fun loadFonts() {
        systemFont = UIManager.getFont("Label.font")
        systemFontSupport = FontLanguageSupport(systemFont)
        try {
            preferredFont = Font(DEFAULT_FONT, Font.PLAIN, 12)
            koreanFont = Font.createFont(
                Font.TRUETYPE_FONT,
                Objects.requireNonNull<InputStream?>(FontManager::class.java.getResourceAsStream("/font/IBMPlexSansKR-Regular.ttf"))
            )
            thaiFont = Font.createFont(
                Font.TRUETYPE_FONT,
                Objects.requireNonNull<InputStream?>(FontManager::class.java.getResourceAsStream("/font/IBMPlexSansThai-Regular.ttf"))
            )
        } catch (ex: FontFormatException) {
            ZLogger.log("Failed to initialize fonts!")
            ZLogger.log(ex.getStackTrace())
        } catch (ex: IOException) {
            ZLogger.log("Failed to initialize fonts!")
            ZLogger.log(ex.getStackTrace())
        }
        checkPreferredFontLanguageSupport()
        if (preferredFont!!.getFontName() == "Dialog.plain") ZLogger.err("[FontManager] Loaded font does not exist!")
        loaded = true
    }

    private fun checkPreferredFontLanguageSupport() {
        preferredFontSupport = FontLanguageSupport(preferredFont)
    }

    fun setPreferredFont(fontName: String?) {
        preferredFont = Font(fontName, Font.PLAIN, 12)
        checkPreferredFontLanguageSupport()
    }

    @JvmStatic
    fun getPreferredFont(): Font {
        return preferredFont!!
    }

    private fun getFont(font: Font, language: FontLanguage): Font? {
        val languageFont = getFont(language)
        return languageFont.deriveFont(font.getStyle(), font.getSize().toFloat())
    }

    private fun getFont(language: FontLanguage): Font {
        if (!loaded) ZLogger.err("Attempted to use the FontManager without first calling FontManager.loadFonts()!")
        when (language) {
            FontLanguage.CHINESE -> {
                if (preferredFontSupport!!.chinese) return preferredFont!!
                if (systemFontSupport!!.chinese) return systemFont!!
            }

            FontLanguage.KOREAN -> {
                if (preferredFontSupport!!.korean) return preferredFont!!
                if (systemFontSupport!!.korean) return systemFont!!
                return koreanFont!!
            }

            FontLanguage.THAI -> {
                if (preferredFontSupport!!.thai) return preferredFont!!
                if (systemFontSupport!!.thai) return systemFont!!
                return thaiFont!!
            }

            FontLanguage.RUSSIAN -> {
                if (preferredFontSupport!!.russian) return preferredFont!!
                if (systemFontSupport!!.russian) return systemFont!!
            }

            else -> {
                return systemFont!!
            }
        }
        if (USE_SYSTEM_DEFAULT) return systemFont!!
        return preferredFont!!
    }

    @JvmStatic
    val allFonts: ArrayList<String>
        get() {
            if (validFonts.isNotEmpty()) return validFonts
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
            for (fontName in ge.getAvailableFontFamilyNames()) {
                val font = Font(fontName, Font.PLAIN, 12)
                val languageSupport = FontLanguageSupport(font)
                if (fontBlacklist.contains(fontName)) continue
                if (languageSupport.english) validFonts.add(fontName)
            }
            return validFonts
        }

    fun isValidFont(targetFont: String?): Boolean {
        for (fontName in allFonts) {
            if (fontName == targetFont) return true
        }
        return false
    }

    @JvmStatic
    fun applyFont(component: JLabel?): JLabel? {
        if (component == null) return null
        val language = UnicodeRange.getLanguage(component.getText())
        component.setFont(getFont(component.getFont(), language))
        return component
    }

    @JvmStatic
    fun applyFont(component: JButton?): JButton? {
        if (component == null) return null
        val language = UnicodeRange.getLanguage(component.getText())
        component.setFont(getFont(component.getFont(), language))
        return component
    }

    @JvmStatic
    fun applyFont(component: JTextField?): JTextField? {
        if (component == null) return null
        val language = UnicodeRange.getLanguage(component.getText())
        component.setFont(getFont(component.getFont(), language))
        return component
    }
}

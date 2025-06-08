package github.zmilla93.core.utility

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * A crude parser for converting basic GitHub Markdown to HTML.
 * Handles headers, links, bold, and italics.
 */
object MarkdownParser {

    private val boldItalicPattern: Pattern = Pattern.compile("(.*)(\\*){3}(.+)(\\*){3}(.*)")
    private val boldPattern: Pattern = Pattern.compile("(.*)(\\*){2}(.+)(\\*){2}(.*)")
    private val italicsPattern: Pattern = Pattern.compile("(.*)(\\*)(.+)(\\*)(.*)")
    private val linkPattern: Pattern = Pattern.compile("(.*)\\[(.+)\\]\\((.+)\\)(.*)")
    private var matcher: Matcher? = null
    private var header = false

    fun getHtmlFromMarkdown(string: String): String {
        var string = string
        header = false
        string = string.trim { it <= ' ' }
        string = convertLinks(string)
        string = convertBoldItalics(string)
        string = convertBold(string)
        string = convertItalics(string)
        string = convertHeader(string)
        if (!header) {
            return string + "<br>"
        }
        return (string)
    }

    //** Convert '# Header' to '<h1>Header<h1>' */
    private fun convertHeader(string: String): String {
        var string = string
        header = true
        if (string.startsWith("####")) {
            string = string.replaceFirst("####".toRegex(), "")
            return "<h4>$string</h4>"
        } else if (string.startsWith("###")) {
            string = string.replaceFirst("###".toRegex(), "")
            return "<h3>$string</h3>"
        } else if (string.startsWith("##")) {
            string = string.replaceFirst("##".toRegex(), "")
            return "<h2>$string</h2>"
        } else if (string.startsWith("#")) {
            string = string.replaceFirst("#".toRegex(), "")
            return "<h1>$string</h1>"
        }
        header = false
        return string
    }

    /** Convert '(link)[Click]' to '<a href="link">Click<a>'  */
    private fun convertLinks(string: String): String {
        var string = string
        matcher = linkPattern.matcher(string)
        while (matcher!!.matches()) {
            string = matcher!!.replaceFirst("$1<a href=\"$3\">$2</a>$4")
            matcher = linkPattern.matcher(string)
        }
        return string
    }

    private fun convertBoldItalics(string: String): String {
        var string = string
        matcher = boldItalicPattern.matcher(string)
        // Bold & Italics
        while (matcher!!.matches()) {
            string = matcher!!.replaceFirst("$1<b><i>$3</i></b>$5")
            matcher = boldItalicPattern.matcher(string)
        }
        return string
    }

    private fun convertBold(string: String): String {
        var string = string
        matcher = boldPattern.matcher(string)
        while (matcher!!.matches()) {
            string = matcher!!.replaceFirst("$1<b>$3</b>$5")
            matcher = boldPattern.matcher(string)
        }
        return string
    }

    private fun convertItalics(string: String): String {
        var string = string
        matcher = italicsPattern.matcher(string)
        while (matcher!!.matches()) {
            string = matcher!!.replaceFirst("$1<i>$3</i>$5")
            matcher = italicsPattern.matcher(string)
        }
        return string
    }
}

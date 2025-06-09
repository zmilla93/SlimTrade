package github.zmilla93.core.utility

import github.zmilla93.App
import github.zmilla93.core.data.PasteReplacement
import github.zmilla93.modules.updater.ZLogger
import java.awt.*
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.io.*
import java.lang.reflect.InvocationTargetException
import java.net.URI
import java.net.URISyntaxException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.swing.Box
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities
import kotlin.math.max

object ZUtil {
    private val clipboard: Clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    private val NUMBER_FORMATTER: NumberFormat = DecimalFormat("##.##")
    private val NUMBER_FORMATTER_ONE_DECIMAL: NumberFormat = DecimalFormat("##.#")

    /** The directory the app is being run in. */
    val workingDirectory: Path = Paths.get(System.getProperty("user.dir"))

    @JvmStatic
    fun readResourceFileAsString(fileName: String): String {
        val resource = javaClass.classLoader.getResource(fileName)
        if (resource == null) throw RuntimeException("Resource not found: $fileName")
        return resource.readText()
    }

//    fun getImageResource(path: String, width: Int? = null, height: Int? = null): Image {
//        val image = ImageIO.read(ZUtil.javaClass.getResourceAsStream(path))
//        val finalHeight = height ?: width
//        if (width != null && finalHeight != null)
//            return image.getScaledInstance(width, finalHeight, Image.SCALE_SMOOTH)
//        return image
//    }


    // FIXME : Move this somewhere better?
    // FIXME : Implement this anytime getTopLevelAncestor is used for packing
    @JvmStatic
    fun packComponentWindow(component: JComponent) {
        val window = component.getTopLevelAncestor()
        if (window is Window) window.pack()
    }

    @JvmStatic
    fun setClipboardContents(text: String?) {
        val pasteString = StringSelection(text)
        try {
            clipboard.setContents(pasteString, null)
        } catch (e: IllegalStateException) {
            ZLogger.err("Failed to set clipboard contents.")
        }
    }


    /** Returns a printable version of an enum name. */
    @JvmStatic
    fun enumToString(input: String): String {
        var input = input
        input = input.replace("_".toRegex(), " ")
        input = input.lowercase(Locale.getDefault())
        val builder = StringBuilder()
        for (i in 0..<input.length) {
            if (i == 0 || input.get(i - 1) == ' ') {
                builder.append(input.get(i).uppercaseChar())
            } else {
                builder.append(input.get(i))
            }
        }
        return builder.toString()
    }

    fun trimArray(array: Array<String?>): Array<String?> {
        for (i in array.indices) {
            array[i] = array[i]!!.trim { it <= ' ' }
        }
        return array
    }

    @JvmStatic
    fun getCommandList(input: String, pasteReplacement: PasteReplacement): ArrayList<String> {
        val commands = ArrayList<String>()
        val builder = StringBuilder()
        for (c in input.toCharArray()) {
            if (c == '/' || c == '@') {
                if (builder.length > 0) commands.add(builder.toString().trim { it <= ' ' })
                builder.setLength(0)
            }
            builder.append(c)
        }
        if (builder.length > 0) commands.add(builder.toString().trim { it <= ' ' })
        for (i in commands.indices) {
            var clean = commands.get(i)
            // FIXME : {zone} replacement
            clean = clean.replace("\\{zone}".toRegex(), App.chatParser.currentZone)
            clean = clean.replace("\\{message}".toRegex(), pasteReplacement.message)
            if (!clean.startsWith("@") && !clean.startsWith("/")) clean =
                "@" + pasteReplacement.playerName + " " + clean
            if (pasteReplacement.playerName != null) clean =
                clean.replace("\\{player}".toRegex(), pasteReplacement.playerName)
            if (pasteReplacement.priceName != null) {
                val itemPrefix =
                    if (pasteReplacement.itemQuantity > 0) pasteReplacement.itemQuantity.toString() + " " else "1 "
                clean = clean.replace("\\{item}".toRegex(), itemPrefix + pasteReplacement.itemName)
            }
            if (pasteReplacement.priceName != null) {
                clean = clean.replace(
                    "\\{price}".toRegex(),
                    NUMBER_FORMATTER.format(pasteReplacement.priceQuantity) + " " + pasteReplacement.priceName
                )
            }
            commands.set(i, clean)
        }
        return commands
    }

    /**
     * Trims a string and normalizes all spaces to 1.
     *
     * @param input String to clean
     * @return Cleaned string
     */
    @JvmStatic
    fun cleanString(input: String): String {
        return input.trim { it <= ' ' }.replace("\\s+".toRegex(), " ")
    }


    fun clamp(value: Int, min: Int, max: Int): Int {
        if (value < min) return min
        if (value > max) return max
        return value
    }

    @JvmStatic
    fun formatNumber(d: Double): String? {
        return NUMBER_FORMATTER.format(d)
    }

    @JvmStatic
    fun formatNumberOneDecimal(d: Double): String? {
        return NUMBER_FORMATTER_ONE_DECIMAL.format(d)
    }


    /**
     * Returns a new GridBagConstraint with gridX and gridY initialized to 0.
     * This is needed to allow incrementing either variable to work correctly.
     *
     * @return GridBagConstraints
     */

    @JvmStatic
    fun getGC(): GridBagConstraints {
        val gc = GridBagConstraints()
        gc.gridx = 0
        gc.gridy = 0
        return gc
    }

    @JvmStatic
    @Deprecated("") // FIXME : Switch to BufferPanel
    fun addStrutsToBorderPanel(panel: JPanel, inset: Int): JPanel {
        return addStrutsToBorderPanel(panel, Insets(inset, inset, inset, inset))
    }

    @JvmStatic
    @Deprecated("") // FIXME : Switch to BufferPanel
    fun addStrutsToBorderPanel(panel: JPanel, insets: Insets): JPanel {
        if (insets.left > 0) panel.add(Box.createHorizontalStrut(insets.left), BorderLayout.WEST)
        if (insets.right > 0) panel.add(Box.createHorizontalStrut(insets.right), BorderLayout.EAST)
        if (insets.top > 0) panel.add(Box.createVerticalStrut(insets.top), BorderLayout.NORTH)
        if (insets.bottom > 0) panel.add(Box.createVerticalStrut(insets.bottom), BorderLayout.SOUTH)
        return panel
    }

    /**
     * Given a point on the screen, returns the bounds of the monitor containing that point.
     *
     * @param point A point on the screen
     * @return Screen bounding rectangle
     */
    @JvmStatic
    fun getScreenBoundsFromPoint(point: Point): Rectangle? {
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val devices = ge.getScreenDevices()
        for (device in devices) {
            val bounds = device.getDefaultConfiguration().getBounds()
            if (bounds.contains(point)) {
                return bounds
            }
        }
        return null
    }

    /**
     * Ensure a cheat sheet has a valid extension, then returns that extension (including period)
     *
     * @return
     */
    fun getCheatSheetExtension(fileName: String): String {
        val index = fileName.lastIndexOf('.')
        val ext = fileName.substring(index)
        return ext
    }

    @JvmStatic
    fun roundTo(value: Int, roundTo: Int): Int {
        return roundTo * (Math.round(value / roundTo.toFloat()))
    }

    @JvmStatic
    fun isEmptyString(input: String): Boolean {
        return input.matches("\\s*".toRegex())
    }

    @JvmStatic
    fun openLink(link: String): Boolean {
        var link = link
        if (link.startsWith("http:")) {
            link = link.replaceFirst("http:".toRegex(), "https:")
        }
        if (!link.startsWith("https://")) {
            link = "https://" + link
        }
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI(link))
                return true
            } catch (e: IOException) {
//                e.printStackTrace();
                return false
            } catch (e: URISyntaxException) {
                return false
            }
        }
        return false
    }

    @JvmStatic
    @Deprecated("") // FIXME : Switch to openFile. Might need a version that allows for directory creation, check use case
    fun openExplorer(path: String) {
        val targetDir = File(path)
        if (!targetDir.exists()) {
            val success = targetDir.mkdirs()
            if (!success) return
        }
        if (!targetDir.exists() || !targetDir.isDirectory()) return
        try {
            Desktop.getDesktop().open(targetDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun openFile(path: Path?) {
        if (path == null) return
        val file = path.toFile()
        if (!file.exists()) return
        try {
            Desktop.getDesktop().open(file)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Deprecated("")
    fun openFile(path: String?) {
        if (path == null) return
        val file = File(path)
        if (!file.exists() || !file.isFile()) return
        try {
            Desktop.getDesktop().open(file)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun getVolumeText(volume: Int): String {
        return if (volume == 0) "Muted" else "" + volume + "%"
    }

    fun printStackTrace() {
        val elements = Thread.currentThread().getStackTrace()
        for (e in elements) {
            System.err.println(e)
        }
    }

    @JvmStatic
    fun <T> printCallingFunction(originClass: Class<T?>) {
        val className = originClass.getSimpleName()
        val elements = Thread.currentThread().getStackTrace()
        var found = false
        for (e in elements) {
            val line = e.toString()
            if (found && !line.contains(className)) {
                System.err.println(e)
                break
            }
            if (line.contains(className)) found = true
        }
    }

    /**
     * A wrapper for SwingUtilities.invokeLater(). If already on the EDT, the function will be called directly.
     */
    // FIXME : Replace current usages of SwingUtilities.invokeLater() with this.
    @JvmStatic
    fun invokeLater(runnable: Runnable) {
        if (SwingUtilities.isEventDispatchThread()) runnable.run()
        else SwingUtilities.invokeLater(runnable)
    }

    /**
     * A wrapper for SwingUtilities.invokeAndWait(). Handles try/catch and can be safely called from EDT.
     *
     * @param runnable Target
     */
    @JvmStatic
    fun invokeAndWait(runnable: Runnable) {
        if (SwingUtilities.isEventDispatchThread()) runnable.run()
        else {
            try {
                SwingUtilities.invokeAndWait(runnable)
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException(e)
            }
        }
    }

    @JvmStatic
    fun fileExists(path: String, isPathRelative: Boolean): Boolean {
        var path = path
        path = cleanPath(path)
        if (isPathRelative) {
            val url = ZUtil::class.java.getResource(path)
            return url != null
        } else {
            val file = File(path)
            return file.exists()
        }
    }

    /**
     * Handles file checking for files that might be stored within the JAR file.
     */
    fun fileExists(path: Path, isPathRelative: Boolean): Boolean {
        if (isPathRelative) {
            val url = ZUtil::class.java.getResource(cleanPath(path.toString()))
            return url != null
        } else {
            return path.toFile().exists()
        }
    }

    fun cleanPath(path: String): String {
        return path.replace("\\\\".toRegex(), "/")
    }

    private fun convertToLocalPath(path: Path): String {
        return path.toString().replace("\\\\".toRegex(), "/")
    }

    /**
     * Returns a BufferedReader for a given file path set to UTF-8 encoding.
     * If given a relative path (starts with "/"), will load the file from the resources folder.
     */
    @JvmStatic
    @Deprecated("")
    fun getBufferedReader(path: String, isPathRelative: Boolean): BufferedReader {
        var path = path
        path = cleanPath(path)
        val inputStream: InputStream?
        try {
            if (isPathRelative) {
                inputStream = Objects.requireNonNull<InputStream?>(ZUtil::class.java.getResourceAsStream(path))
            } else {
                try {
                    inputStream = Files.newInputStream(Paths.get(path))
                } catch (e: IOException) {
                    ZLogger.err("File not found: " + path)
                    throw RuntimeException("File not found: " + path + " (relative: " + isPathRelative + ")")
                }
            }
        } catch (e: NullPointerException) {
            throw RuntimeException("File not found: " + path + " (relative: " + isPathRelative + ")")
        }
        return BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
    }

    @JvmStatic
    @Deprecated("")
    fun getBufferedWriter(path: String): BufferedWriter {
        var path = path
        path = cleanPath(path)
        val outputStream: OutputStream
        val file = File(path).getParentFile()
        if (!file.exists() && !file.mkdirs()) throw RuntimeException("Failed to create directory for file writer: " + path)
        try {
            outputStream = Files.newOutputStream(Paths.get(path))
        } catch (e: IOException) {
            throw RuntimeException("Failed to open file writer: " + path)
        }
        return BufferedWriter(OutputStreamWriter(outputStream, StandardCharsets.UTF_8))
    }

    @JvmStatic
    fun getBufferedReader(path: Path, isPathRelative: Boolean): BufferedReader {
        val inputStream: InputStream?
        try {
            if (isPathRelative) {
                inputStream = Objects.requireNonNull<InputStream?>(
                    ZUtil::class.java.getResourceAsStream(
                        convertToLocalPath(path)
                    )
                )
            } else {
                try {
                    inputStream = Files.newInputStream(path)
                } catch (e: IOException) {
                    ZLogger.err("File not found: " + path)
                    throw RuntimeException("File not found: " + path)
                }
            }
        } catch (e: NullPointerException) {
            throw RuntimeException("File not found: " + path + " (relative: " + isPathRelative + ")")
        }
        return BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
    }

    fun getBufferedWriter(path: Path): BufferedWriter {
        val outputStream: OutputStream
        if (!path.getParent().toFile().exists()) {
            try {
                Files.createDirectories(path)
            } catch (e: IOException) {
                throw RuntimeException("Failed to create directory for file writer: " + path)
            }
        }
        try {
            outputStream = Files.newOutputStream(path)
        } catch (e: IOException) {
            throw RuntimeException("Failed to open file writer: " + path)
        }
        return BufferedWriter(OutputStreamWriter(outputStream, StandardCharsets.UTF_8))
    }

    @JvmStatic
    fun getFileAsString(path: String, isPathRelative: Boolean): String? {
        val builder = StringBuilder()
        val reader = getBufferedReader(path, isPathRelative)
        try {
            while (reader.ready()) {
                builder.append(reader.readLine())
            }
            reader.close()
            return builder.toString()
        } catch (e: IOException) {
            return null
        }
    }

    @JvmStatic
    fun csvToIntArray(text: String): IntArray {
        val stringValues = trimArray(text.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val intValues = IntArray(stringValues.size)
        for (i in stringValues.indices) {
            intValues[i] = stringValues[i]!!.toInt()
        }
        return intValues
    }

    @JvmStatic
    fun clearTransparentComponent(g: Graphics?, component: Component) {
        val g2d = g as Graphics2D
        g2d.setComposite(AlphaComposite.Clear)
        g2d.fillRect(0, 0, component.getWidth(), component.getHeight())
        g2d.setComposite(AlphaComposite.SrcOver)
    }

    @JvmStatic
    val maxSpeedExecutor: ExecutorService
        get() = Executors.newFixedThreadPool(
            max(
                1,
                Runtime.getRuntime().availableProcessors() - 1
            )
        )

    @JvmStatic
    fun removeFromParent(component: Component) {
        val parent = component.getParent()
        if (parent != null) parent.remove(component)
    }

    /**
     * A nullable version of [Paths.get].
     * Only allows a single string parameter.
     */
    @JvmStatic
    fun getPath(pathString: String?): Path? {
        if (pathString == null) return null
        return Paths.get(pathString)
    }
}

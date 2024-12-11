package github.zmilla93.core.utility;

import github.zmilla93.App;
import github.zmilla93.core.data.PasteReplacement;
import github.zmilla93.modules.updater.ZLogger;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZUtil {

    private ZUtil() {
        /// Static class
    }

    private static final NumberFormat NUMBER_FORMATTER = new DecimalFormat("##.##");
    private static final NumberFormat NUMBER_FORMATTER_ONE_DECIMAL = new DecimalFormat("##.#");

    // FIXME : Move this somewhere better?
    // FIXME : Implement this anytime getTopLevelAncestor is used for packing
    public static void packComponentWindow(JComponent component) {
        Container window = component.getTopLevelAncestor();
        if (window instanceof Window) ((Window) window).pack();
    }

    /**
     * Returns a printable version of an enum name.
     *
     * @param input
     * @return
     */
    public static String enumToString(String input) {
        input = input.replaceAll("_", " ");
        input = input.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (i == 0 || input.charAt(i - 1) == ' ') {
                builder.append(Character.toUpperCase(input.charAt(i)));
            } else {
                builder.append(input.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String[] trimArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }
        return array;
    }

    public static ArrayList<String> getCommandList(String input, PasteReplacement pasteReplacement) {
        ArrayList<String> commands = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c == '/' || c == '@') {
                if (builder.length() > 0)
                    commands.add(builder.toString().trim());
                builder.setLength(0);
            }
            builder.append(c);
        }
        if (builder.length() > 0)
            commands.add(builder.toString().trim());
        for (int i = 0; i < commands.size(); i++) {
            String clean = commands.get(i);
            clean = clean.replaceAll("\\{zone}", App.chatParser.getCurrentZone());
            clean = clean.replaceAll("\\{message}", pasteReplacement.message);
            if (!clean.startsWith("@") && !clean.startsWith("/"))
                clean = "@" + pasteReplacement.playerName + " " + clean;
            if (pasteReplacement.playerName != null)
                clean = clean.replaceAll("\\{player}", pasteReplacement.playerName);
            if (pasteReplacement.priceName != null) {
                String itemPrefix = pasteReplacement.itemQuantity > 0 ? pasteReplacement.itemQuantity + " " : "1 ";
                clean = clean.replaceAll("\\{item}", itemPrefix + pasteReplacement.itemName);
            }
            if (pasteReplacement.priceName != null) {
                clean = clean.replaceAll("\\{price}", NUMBER_FORMATTER.format(pasteReplacement.priceQuantity) + " " + pasteReplacement.priceName);
            }
            commands.set(i, clean);
        }
        return commands;
    }

    /**
     * Trims a string and normalizes all spaces to 1.
     *
     * @param input String to clean
     * @return Cleaned string
     */
    public static String cleanString(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }


    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static String formatNumber(double d) {
        return NUMBER_FORMATTER.format(d);
    }

    public static String formatNumberOneDecimal(double d) {
        return NUMBER_FORMATTER_ONE_DECIMAL.format(d);
    }


    /**
     * Returns a new GridBagConstraint with gridX and gridY initialized to 0.
     * This is needed to allow incrementing either variable to work correctly.
     *
     * @return GridBagConstraints
     */
    public static GridBagConstraints getGC() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        return gc;
    }

    @Deprecated
    // FIXME : Switch to BufferPanel
    public static JPanel addStrutsToBorderPanel(JPanel panel, int inset) {
        return addStrutsToBorderPanel(panel, new Insets(inset, inset, inset, inset));
    }

    @Deprecated
    // FIXME : Switch to BufferPanel
    public static JPanel addStrutsToBorderPanel(JPanel panel, Insets insets) {
        if (insets.left > 0) panel.add(Box.createHorizontalStrut(insets.left), BorderLayout.WEST);
        if (insets.right > 0) panel.add(Box.createHorizontalStrut(insets.right), BorderLayout.EAST);
        if (insets.top > 0) panel.add(Box.createVerticalStrut(insets.top), BorderLayout.NORTH);
        if (insets.bottom > 0) panel.add(Box.createVerticalStrut(insets.bottom), BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Given a point on the screen, returns the bounds of the monitor containing that point.
     *
     * @param point A point on the screen
     * @return Screen bounding rectangle
     */
    public static Rectangle getScreenBoundsFromPoint(Point point) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for (GraphicsDevice device : devices) {
            Rectangle bounds = device.getDefaultConfiguration().getBounds();
            if (bounds.contains(point)) {
                return bounds;
            }
        }
        return null;
    }

    /**
     * Ensure a cheat sheet has a valid extension, then returns that extension (including period)
     *
     * @return
     */
    public static String getCheatSheetExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        String ext = fileName.substring(index);
        return ext;
    }

    public static int roundTo(int value, int roundTo) {
        return roundTo * (Math.round(value / (float) roundTo));
    }

    public static boolean isEmptyString(String input) {
        return input.matches("\\s*");
    }

    public static boolean openLink(String link) {
        if (link.startsWith("http:")) {
            link = link.replaceFirst("http:", "https:");
        }
        if (!link.startsWith("https://")) {
            link = "https://" + link;
        }
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(link));
                return true;
            } catch (IOException | URISyntaxException e) {
//                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    // FIXME : Switch to path!
    public static void openExplorer(String path) {
        File targetDir = new File(path);
        if (!targetDir.exists()) {
            boolean success = targetDir.mkdirs();
            if (!success) return;
        }
        if (!targetDir.exists() || !targetDir.isDirectory()) return;
        try {
            Desktop.getDesktop().open(targetDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openFile(String path) {
        if (path == null) return;
        File file = new File(path);
        if (!file.exists() || !file.isFile()) return;
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getVolumeText(int volume) {
        return volume == 0 ? "Muted" : "" + volume + "%";
    }

    public static void printStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : elements) {
            System.err.println(e);
        }
    }

    public static <T> void printCallingFunction(Class<T> originClass) {
        String className = originClass.getSimpleName();
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        boolean found = false;
        for (StackTraceElement e : elements) {
            String line = e.toString();
            if (found && !line.contains(className)) {
                System.err.println(e);
                break;
            }
            if (line.contains(className)) found = true;
        }
    }

    /**
     * A wrapper for SwingUtilities.invokeAndWait(). Handles try/catch and can be safely called from EDT.
     *
     * @param runnable Target
     */
    public static void invokeAndWait(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) runnable.run();
        else {
            try {
                SwingUtilities.invokeAndWait(runnable);
            } catch (InterruptedException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public static boolean fileExists(Path path, boolean isPathRelative) {
//        path = cleanPath(path);
//        if (isPathRelative) {
//            URL url = ZUtil.class.getResource(path);
//            return url != null;
//        } else {
//            File file = new File(path);
//            return file.exists();
//        }
//    }

    public static boolean fileExists(String path, boolean isPathRelative) {
        path = cleanPath(path);
        if (isPathRelative) {
            URL url = ZUtil.class.getResource(path);
            return url != null;
        } else {
            File file = new File(path);
            return file.exists();
        }
    }

    /**
     * Handles file checking for files that might be stored within the JAR file.
     */
    public static boolean fileExists(Path path, boolean isPathRelative) {
        if (isPathRelative) {
            URL url = ZUtil.class.getResource(cleanPath(path.toString()));
            return url != null;
        } else {
            return path.toFile().exists();
        }
    }

    public static String cleanPath(String path) {
        return path.replaceAll("\\\\", "/");
    }

    /**
     * Returns a BufferedReader for a given file path set to UTF-8 encoding.
     * If given a relative path (starts with "/"), will load the file from the resources folder.
     */
    public static BufferedReader getBufferedReader(String path, boolean isPathRelative) {
        path = cleanPath(path);
        InputStream inputStream;
        try {
            if (isPathRelative) {
                inputStream = Objects.requireNonNull(ZUtil.class.getResourceAsStream(path));
            } else {
                try {
                    inputStream = Files.newInputStream(Paths.get(path));
                } catch (IOException e) {
                    ZLogger.err("File not found: " + path);
                    throw new RuntimeException("File not found: " + path + " (relative: " + isPathRelative + ")");
                }
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("File not found: " + path + " (relative: " + isPathRelative + ")");
        }
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public static BufferedWriter getBufferedWriter(String path) {
        path = cleanPath(path);
        OutputStream outputStream;
        File file = new File(path).getParentFile();
        if (!file.exists() && !file.mkdirs())
            throw new RuntimeException("Failed to create directory for file writer: " + path);
        try {
            outputStream = Files.newOutputStream(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to open file writer: " + path);
        }
        return new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
    }

    public static String getFileAsString(String path, boolean isPathRelative) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = ZUtil.getBufferedReader(path, isPathRelative);
        try {
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static int[] csvToIntArray(String text) {
        String[] stringValues = trimArray(text.split(","));
        int[] intValues = new int[stringValues.length];
        for (int i = 0; i < stringValues.length; i++) {
            intValues[i] = Integer.parseInt(stringValues[i]);
        }
        return intValues;
    }

    public static void clearTransparentComponent(Graphics g, Component component) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, component.getWidth(), component.getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);
    }

    public static ExecutorService getMaxSpeedExecutor() {
        return Executors.newFixedThreadPool(Math.max(1, Runtime.getRuntime().availableProcessors() - 1));
    }

    /**
     * A nullable version of {@link Paths#get(String, String...)}.
     * Only allows a single string parameter.
     */
    @Nullable
    public static Path getPath(@Nullable String pathString) {
        if (pathString == null) return null;
        return Paths.get(pathString);
    }

}

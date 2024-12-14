package github.zmilla93.core.utility;

import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.enums.ExpandDirection;
import github.zmilla93.core.managers.AudioManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

// TODO : CLEAN UP THIS FILE
public class TradeUtil {

    private static final Desktop desktop = Desktop.getDesktop();

    private TradeUtil() {
        /// Static class
    }

    /**
     * Verify that a given path points to the Path of Exile 1 or 2's install directory.
     * Checks that the path isn't null, is a directory that ends with Path of Exile 1 or 2, and contains a 'logs' subfolder
     */
    // FIXME : Client.txt check?
    public static boolean isValidPOEFolder(Path path, Game game) {
        if (path == null) return false;
        if (!path.endsWith(game.toString())) return false;
        File file = path.toFile();
        boolean validFolder = file.exists() && file.isDirectory();
        if (!validFolder) return false;
        return path.resolve(SaveManager.POE_LOG_FOLDER_NAME).toFile().exists();
    }

    public static String getFixedItemName(String item, double count, boolean paren) {
        String fixedNum = count == 0 ? "" : String.valueOf(count).toString().replaceAll("[.,]0", "");
        if (!fixedNum.equals("") && paren) {
            fixedNum = "(" + fixedNum + ")";
        }
        String fixedString = fixedNum.equals("") ? item : fixedNum + " " + item;
        return fixedString;
    }

    public static String getFixedDouble(double num, boolean paren) {
        String fixedDouble = String.valueOf(num).replaceAll("[.,]0", "");
        if (paren) {
            fixedDouble = "(" + fixedDouble + ")";
        }
        return fixedDouble;
    }

    public static void applyAnchorPoint(Window window, Point point, Anchor anchor) {
        Point adjustedPoint = new Point(point.x, point.y);
        if (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT) adjustedPoint.x -= window.getWidth();
        if (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT) adjustedPoint.y -= window.getHeight();
        window.setLocation(adjustedPoint);
    }

    public static void applyAnchorPoint(Window window, Point point, ExpandDirection expandDirection) {
        Point adjustedPoint = new Point(point.x, point.y);
        if (expandDirection == ExpandDirection.UPWARDS) adjustedPoint.y -= window.getHeight();
        window.setLocation(adjustedPoint);
    }

    public static int getAudioPercent(float f) {
        f = f + AudioManager.RANGE - AudioManager.MAX_VOLUME;
        int i = (int) ((f / AudioManager.RANGE) * 100);
        return i;
    }

    public static float getAudioVolume(int i) {
        float f = (float) ((AudioManager.RANGE / 100.0) * (float) (i));
        return f - AudioManager.RANGE + AudioManager.MAX_VOLUME;
    }

    public static String cleanItemName(String input) {
        if (input == null) return null;
        String cleanString = input.replaceAll("(?i)superior( )?", "")
                .replaceAll("( )?\\(.+\\)", "")
                .replaceAll(",", "");
        if (cleanString.contains(" Map")) cleanString = cleanString.replaceFirst(" Map", "");
        return cleanString;
    }

    public static String fixCasing(String input) {
        String inputArray[] = input.toLowerCase().split("\\s");
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : inputArray) {
            if (i > 0) {
                stringBuilder.append(" ");
            }
            if (!str.equals("of") && !str.equals("the")) {
                inputArray[i] = str.substring(0, 1).toUpperCase() + str.substring(1);
            }
            stringBuilder.append(inputArray[i]);
            i++;
        }
        return stringBuilder.toString();
    }

    public static int intWithinRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float floatWithinRange(float value, float min, float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    public static String capitalizeString(String s) {
        return capitalizeString(s, false);
    }

    public static String capitalizeString(String s, boolean replaceUnderscores) {
        StringBuilder builder = new StringBuilder();
        boolean cap = true;
        for (char c : s.toCharArray()) {
            if (cap) {
                builder.append(Character.toUpperCase(c));
                cap = false;
            } else if (c == '_' || c == ' ') {
                builder.append(c);
                cap = true;
            } else {
                builder.append(Character.toLowerCase(c));
            }
        }
        if (replaceUnderscores) {
            return builder.toString().replaceAll("_", " ");
        }
        return builder.toString();
    }

    public static boolean isValidImagePath(String path) {
        File f = new File(path);
        String contentType = null;
        try {
            contentType = Files.probeContentType(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!f.exists() || contentType == null || !contentType.startsWith("image")) {
            return false;
        }
        return true;
    }

    public static void openLink(String urlString) {
        try {
            URL url = new URL(urlString);
            openLink(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void openLink(URL url) {
        try {
            desktop.browse(url.toURI());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void openFolder(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static int dateDifference(Date d1, Date d2) {
        try {
            long diff = d2.getTime() - d1.getTime();
            if (diff < 0) {
                return -1;
            }

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;

            diffMinutes += diffHours * 60;
            if (diffSeconds > 30) {
                diffMinutes++;
            }
            return (int) diffMinutes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

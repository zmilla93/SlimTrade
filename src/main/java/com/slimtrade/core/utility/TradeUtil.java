package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;
import java.util.Random;

// TODO : CLEAN UP THIS FILE
public class TradeUtil {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static Desktop desktop = Desktop.getDesktop();

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

    public static void changeCharacterName() {
        if (SaveManager.settingsSaveFile.data.characterName == null) return;
        App.chatParser.startChangeCharacterName();
        Random random = new Random();
        int rng = random.nextInt(1000000, 9999999);
        POEInterface.runCommand("@{self} Change Character #" + rng, new PasteReplacement(SaveManager.settingsSaveFile.data.characterName, null));
    }

    public static void changeCharacterName(String newName) {
        System.out.println("NEW CHAR SET ::: " + newName);
        SaveManager.settingsSaveFile.data.characterName = newName;
        SaveManager.settingsSaveFile.saveToDisk(false);
        SwingUtilities.invokeLater(() -> FrameManager.optionsWindow.refreshCharacterName());
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
        if (input == null) {
            return null;
        }
        String cleanString = input.replaceAll("(?i)superior( )?", "").replaceAll("( )?\\(.+\\)", "");
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

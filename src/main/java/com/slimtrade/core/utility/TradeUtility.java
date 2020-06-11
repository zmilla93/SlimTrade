package com.slimtrade.core.utility;

import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.enums.POEImage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TradeUtility {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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

    public static POEImage getPOEImage(String input) {
        if (input == null) {
            return null;
        }
        input = input.toLowerCase();
        for (POEImage poeImage : POEImage.values()) {
            if (poeImage.getTags() != null) {
                for (String tag : poeImage.getTags()) {
                    if (tag.toLowerCase().equals(input.toLowerCase())) {
                        return poeImage;
                    }
                }
            }
        }
        return null;
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

    // TODO : check more stuff?
    // TODO : THIS THROWS AN ERROR IF A VALUE IS NULL
    // TODO : Add chat scanner messages
    public static boolean isMatchingTrades(TradeOffer trade1, TradeOffer trade2) {
        if (trade1.messageType != trade2.messageType) {
            return false;
        }
        int totalCheckCount;
        int check = 0;
        // Incoming & Outgoing Trades
        if (trade1.messageType == MessageType.INCOMING_TRADE || trade1.messageType == MessageType.OUTGOING_TRADE) {
            totalCheckCount = 5;
            if (trade1.playerName.equals(trade2.playerName)) {
                check++;
            }
            if (trade1.itemName.equals(trade2.itemName)) {
                check++;
            }
            if (trade1.itemQuantity.equals(trade2.itemQuantity)) {
                check++;
            }
            if ((trade1.priceTypeString == null || trade2.priceTypeString == null)) {
                if ((trade1.priceTypeString == null && trade2.priceTypeString == null)) {
                    check++;
                }
            } else if (trade1.priceTypeString.equals(trade2.priceTypeString)) {
                check++;
            }
            if (trade1.priceQuantity.equals(trade2.priceQuantity)) {
                check++;
            }
            return check == totalCheckCount;
        }
        // Chat Scanner Trades
        else if (trade1.messageType == MessageType.CHAT_SCANNER) {
            return trade1.playerName.equals(trade2.playerName);
        }
        return false;
    }

    public static boolean isValidImagePath(String path) {
        File f = new File(path);
        String contentType = null;
        try {
            contentType = Files.probeContentType(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!f.exists() || contentType == null || !contentType.startsWith("image")) {
            return false;
        }
        return true;
    }

}

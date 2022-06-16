package com.slimtrade.core.utility;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ZUtil {

    private static NumberFormat numberFormatter = new DecimalFormat("##.##");

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

    public static ArrayList<String> getCommandList(String input, TradeOffer tradeOffer) {
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
            if (!clean.startsWith("@") && !clean.startsWith("/")) clean = "@" + tradeOffer.playerName + " " + clean;
            clean = clean.replaceAll("\\{self}", SaveManager.settingsSaveFile.data.characterName);
            clean = clean.replaceAll("\\{player}", tradeOffer.playerName);
            String itemPrefix = tradeOffer.itemQuantity > 0 ? tradeOffer.itemQuantity + " " : "1 ";
            clean = clean.replaceAll("\\{item}", itemPrefix + tradeOffer.itemName);
            clean = clean.replaceAll("\\{price}", numberFormatter.format(tradeOffer.priceQuantity) + " " + tradeOffer.priceTypeString);
//            System.out.println("MESSAGE:" + tradeOffer.message);
//            clean = clean.replaceAll("[{]message[}]", Matcher.quoteReplacement(tradeOffer.message));
            // FIXME:
//            clean = clean.replaceAll("\\{zone}", tradeOffer.playerName);
            commands.set(i, clean);
        }
        System.out.println("COMMANDS :: " + commands);
        return commands;
    }

    /**
     * Returns a new GridBagConstraint with gridX and gridY initialized to 0.
     * This is needed to allow incrementing either variable to work correctly.
     *
     * @return
     */
    public static GridBagConstraints getGC() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        return gc;
    }

    /**
     * Given a point on the screen, returns the bounds of the monitor containing that point.
     *
     * @param point
     * @return
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
        System.out.println(ext);
        return ext;
    }

    public static boolean isEmptyString(String input) {
        return input.matches("\\s*");
    }

    /**
     * Given a panel with a border layout, will add insets to the edges of the panel
     *
     * @param panel
     */
    public static void addBorderStruts(JPanel panel, Insets insets) {
        panel.add(Box.createVerticalStrut(insets.top), BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(insets.bottom), BorderLayout.SOUTH);
        panel.add(Box.createHorizontalStrut(insets.left), BorderLayout.WEST);
        panel.add(Box.createHorizontalStrut(insets.right), BorderLayout.EAST);
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

}

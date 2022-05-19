package com.slimtrade.core.utility;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ZUtil {

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
            clean = clean.replaceAll("\\{self}", SaveManager.settingsSaveFile.data.characterName);
            clean = clean.replaceAll("\\{player}", tradeOffer.playerName);
            clean = clean.replaceAll("\\{item}", tradeOffer.playerName);
            clean = clean.replaceAll("\\{price}", tradeOffer.playerName);
            clean = clean.replaceAll("\\{message}", tradeOffer.playerName);
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

}

package com.slimtrade.core.observing.poe;

import com.slimtrade.core.utility.PoeInterface;

import java.util.ArrayList;

public class CommandManager {

    public static void runCommand(String[] commands, String self, String player, String item, String price) {
        new Thread(() -> {
            for(String cmd : commands) {
                System.out.println("RUNNING : " + cmd);
                cmd = cmd.replaceAll("\\{self\\}", self);
                cmd = cmd.replaceAll("\\{player\\}", player);
                cmd = cmd.replaceAll("\\{item\\}", item);
                cmd = cmd.replaceAll("\\{price\\}", price);
                PoeInterface.pasteWithFocus(cmd);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String[] getCommandList(String text) {
        ArrayList<String> commands = new ArrayList<>();
        StringBuilder builder = new StringBuilder(255);
        if (!text.startsWith("/") && !text.startsWith("@")) {
            text = "@{player} " + text;
        }
        int i = 0;
        while (i < text.length()) {
            if ((text.charAt(i) == '@' || text.charAt(i) == '/') && builder.length() > 1) {
                commands.add(builder.toString().trim());
                builder.setLength(0);
            }
            builder.append(text.charAt(i));
            i++;
        }
        if (builder.length() > 1) {
            commands.add(builder.toString().trim());
            builder.setLength(0);
        }
        return commands.toArray(new String[0]);
    }

}

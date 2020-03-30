package com.slimtrade.enums;

public enum QuickPasteSetting {

    DISABLED("Disabled", "Enable this feature to quickly paste trade messages into Path of Exile."),
    HOTKEY("Hotkey", "Copy a trade message, then press the hotkey to paste the message into Path of Exile."),
    AUTOMATIC("Automatic", "SlimTrade will detect when a trade message is copied and automatically paste it into Path of Exile.");

    private String name;
    private String info;

    QuickPasteSetting(String name, String info) {
        this.name = name;
        this.info = info;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getInfo() {
        return this.info;
    }

}

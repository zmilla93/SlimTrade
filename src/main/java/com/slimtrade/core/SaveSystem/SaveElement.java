package com.slimtrade.core.SaveSystem;

import java.awt.*;

public class SaveElement {

    public String key;
    public String value;
    public Component uiElement;

    public SaveElement(String key, Component uiElement){
        this.key = key;
        this.uiElement = uiElement;
    }

}

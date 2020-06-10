package com.slimtrade.core.saving;

import com.slimtrade.core.saving.elements.PinElement;

import java.util.ArrayList;

public class PinSaveFile {

    public PinElement optionsPin = new PinElement();
    public PinElement historyPin = new PinElement();
    public PinElement chatScannerPin = new PinElement();
    public ArrayList<PinElement> customImagePins = new ArrayList<>();

}

package com.slimtrade.core.observing;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CustomKeyboardListener {

    private static volatile boolean ctrlPressed = false;

    public static boolean isCtrlPressed() {
        synchronized (CustomKeyboardListener.class) {
            return ctrlPressed;
        }
    }

    public CustomKeyboardListener() {
//        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
//            synchronized (CustomKeyboardListener.class) {
//                System.out.println("CHAR : " + e.getKeyChar());
//                System.out.println("CODE : " + e.getKeyCode());
//                switch (e.getID()) {
//                    case KeyEvent.KEY_PRESSED:
//                        if (e.getKeyCode() == KeyEvent.CTRL_DOWN_MASK) {
//                            ctrlPressed = true;
//                        }
//                        break;
//                    case KeyEvent.KEY_RELEASED:
//                        if (e.getKeyCode() == KeyEvent.CTRL_DOWN_MASK) {
//                            ctrlPressed = false;
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
    }

}

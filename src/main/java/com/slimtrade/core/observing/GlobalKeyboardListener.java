package com.slimtrade.core.observing;

import com.slimtrade.core.utility.PoeInterface;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyboardListener implements NativeKeyListener{

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

//		System.out.println("Key Pressed!");
//		System.out.println("\t" + NativeKeyEvent.getKeyText(e.getKeyCode()));
//		System.out.println("\t" + NativeKeyEvent.getModifiersText(e.getModifiers()));

		if(e.getKeyCode() == NativeKeyEvent.VC_F2) {
			PoeInterface.attemptQuickPaste();
		}

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

}
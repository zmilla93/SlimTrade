package main.java.com.slimtrade.core;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.sun.jna.Native;
import com.sun.jna.PointerType;

public class GlobalKeyboardListener implements NativeKeyListener{

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
//		System.out.println("!");
//		System.out.println(e.getKeyCode());
//		System.out.println(e.getKeyText());
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

	
}
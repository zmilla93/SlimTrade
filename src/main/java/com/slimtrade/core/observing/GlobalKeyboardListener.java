package main.java.com.slimtrade.core.observing;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.sun.jna.Native;
import com.sun.jna.PointerType;

public class GlobalKeyboardListener implements NativeKeyListener{

	private boolean controlPressed;
	private boolean shiftPressed;
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_CONTROL){
			controlPressed = true;
		}else if(e.getKeyCode() == NativeKeyEvent.VC_SHIFT){
			shiftPressed = true;
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_CONTROL){
			controlPressed = false;
		}else if(e.getKeyCode() == NativeKeyEvent.VC_SHIFT){
			shiftPressed = false;
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		System.out.println("TYPED");
	}

	public boolean isControlPressed(){
		return controlPressed;
	}

	public boolean isShiftPressed() {
		return shiftPressed;
	}
}
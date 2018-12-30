package com.zrmiller.slimtrade;

import java.awt.AWTException;

public class Main {	
	
	public static void main(String[] args) {
		
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Overlay overlay = new Overlay();
		
	}

}

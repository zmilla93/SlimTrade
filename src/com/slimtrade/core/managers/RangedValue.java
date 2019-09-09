package com.slimtrade.core.managers;

public enum RangedValue {

	Test(50, 0, 100),
	Test2(50, 0, 100),
	;
	
	private int MINIMUM_VALUE;
	private int MAXIMUM_VALUE;
	private int DEFAULT_VALUE;
		
	RangedValue(int def, int min, int max){
		this.MINIMUM_VALUE = min;
		this.MAXIMUM_VALUE = max;
		this.DEFAULT_VALUE = def;
	}
	
}

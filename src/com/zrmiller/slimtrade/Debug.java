package com.zrmiller.slimtrade;

public class Debug {

	public Debug(){
		
	}
	
	public static void print(String... str){
		for(int i=0;i<str.length;i++){
			System.out.println(str[i]);
		}
	}
	
	public static void print(int... num){
		for(int i=0;i<num.length;i++){
			System.out.println(num[i]);
		}
	}
	
}

package com.zrmiller.slimtrade.debug;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Debug extends JFrame{

	private static final long serialVersionUID = 1L;
	private JScrollPane logScrollPane;
	private JTextArea logTextArea = new JTextArea();

	public Debug(){
		this.setTitle("SlimTrade Debugger");
		this.setBounds(0, 0, 400, 400);
		this.setVisible(true);
		logScrollPane = new JScrollPane(logTextArea);
		this.add(logScrollPane);
	}
	
	public void log(String... text){
//		System.out.println("TEXT : " + logTextArea.getText());
		if(!logTextArea.getText().equals("")){
						for(int i = 0;i<text.length;i++){
				logTextArea.setText(logTextArea.getText() + "\n" + text[i]);
			}
			
		}else{
			logTextArea.setText(text[0]);
			if(text.length>1){
				for(int i = 0;i<text.length;i++){
				logTextArea.setText(logTextArea.getText() + "\n" + text[i]);
				}
			}
		}
	}
	
	public void clearLog(){
		logTextArea.setText("");
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

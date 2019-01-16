package main.java.com.slimtrade.debug;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.java.com.slimtrade.core.FrameManager;

public class Debugger extends JFrame{

	private static final long serialVersionUID = 1L;
	private JScrollPane logScrollPane;
	private JTextArea logTextArea = new JTextArea();

	public Debugger(){
		this.setTitle("SlimTrade Debugger");
		this.setBounds(0, 0, 800, 500);
		this.setVisible(true);
//		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		logScrollPane = new JScrollPane(logTextArea);
		logScrollPane.setPreferredSize(new Dimension(750, 400));
		this.add(logScrollPane);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setAlignmentX(CENTER_ALIGNMENT);
		this.add(refreshButton);
		
		refreshButton.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				FrameManager.forceAllToTop();
			}
		});
		
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

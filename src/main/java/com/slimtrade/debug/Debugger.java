package main.java.com.slimtrade.debug;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.enums.PreloadedImage;

public class Debugger extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JScrollPane logScrollPane;
	private static JTextArea logTextArea = new JTextArea();

	private static long start;
	private static long end;
	
	public Debugger() {
		this.setTitle("SlimTrade Debugger");
		this.setBounds(0, 0, 800, 500);
		this.setVisible(true);
		// this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		logScrollPane = new JScrollPane(logTextArea);
		logScrollPane.setPreferredSize(new Dimension(750, 400));
		this.add(logScrollPane);
		
//		ImageIcon img = new ImageIcon();
//		this.setIconImage(PreloadedImage.TAG.getImage());

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton refreshButton = new JButton("Refresh");
		refreshButton.setAlignmentX(CENTER_ALIGNMENT);
		this.add(refreshButton);

		refreshButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				FrameManager.forceAllToTop();
			}
		});
		
		this.revalidate();
		this.repaint();

	}

	public static void log(String... text) {
		if(!Main.debugMode){
			return;
		}
		if (!logTextArea.getText().equals("")) {
			for (int i = 0; i < text.length; i++) {
				logTextArea.setText(logTextArea.getText() + "\n" + text[i]);
			}
		} else {
			logTextArea.setText(text[0]);
			if (text.length > 1) {
				for (int i = 0; i < text.length; i++) {
					logTextArea.setText(logTextArea.getText() + "\n" + text[i]);
				}
			}
		}
	}

	public void clearLog() {
		if(!Main.debugMode){
			return;
		}
		logTextArea.setText("");
	}

	public static void print(String... str) {
		if(!Main.debugMode){
			return;
		}
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
	}

	public static void print(int... num) {
		if(!Main.debugMode){
			return;
		}
		for (int i = 0; i < num.length; i++) {
			System.out.println(num[i]);
		}
	}
	
	//Benchmarking has 1 millisecond of variance
	public static void benchmarkStart(){
		start = System.currentTimeMillis();
	}
	
	public static long benchmark(){
		return System.currentTimeMillis()-start;
	}

}

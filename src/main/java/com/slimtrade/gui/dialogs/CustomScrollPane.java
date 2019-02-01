package main.java.com.slimtrade.gui.dialogs;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollPane extends JScrollPane{
	
	private static final long serialVersionUID = 1L;

	public CustomScrollPane(JPanel panel){
		super(panel);
		this.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
		
		@Override
		protected void configureScrollBarColors(){
            this.thumbColor = Color.GREEN;
            this.thumbDarkShadowColor = Color.BLACK;
            this.thumbLightShadowColor = Color.DARK_GRAY;
        }
		
	});;
	}

}

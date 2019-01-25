package main.java.com.slimtrade.windows;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import main.java.com.slimtrade.event.InfoButtonEvent;
import main.java.com.slimtrade.event.InfoButtonListener;

public class UNUSED_BasicInfoEventTesting extends BasicWindowDialog {

	private static final long serialVersionUID = 1L;
	private JLabel infoLabel = new JLabel();
	private JPanel buttonPanel = new JPanel();
	
	public UNUSED_BasicInfoEventTesting(String title) {
		//TODO : Modify Colors
		super(title);
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		titlebarPanel.setPreferredSize(new Dimension(defaultWidth, titlebarHeight));
		container.add(infoLabel);
		container.add(buttonPanel);
		
		BasicInfoButton b = new BasicInfoButton();
		
		this.revalidate();
		this.repaint();
	}
	
	public void resizeWindow(int width, int height){
		this.setSize(width, height+titlebarHeight);
		container.setPreferredSize(new Dimension(width, height));
		titlebarContainer.setPreferredSize(new Dimension(width, titlebarHeight));
		titlebarPanel.setPreferredSize(new Dimension(width, titlebarHeight));
		this.revalidate();
		this.repaint();
	}
	
	public void addButton(){
		
	}
	
	class BasicInfoButton{
		
		public BasicInfoButton(){
//			InfoButtonListener l = new InfoButtonListener();
			this.addInfoButtonListener(new InfoButtonListener(){
				public void infoButtonEventOccurred(InfoButtonEvent e) {
					
				}
			});
			fireInfoButtonEvent(new InfoButtonEvent(this));
		}
		
		private EventListenerList listenerList = new EventListenerList();
		
		public void fireInfoButtonEvent(InfoButtonEvent event){
			Object[] listeners = listenerList.getListenerList();
			
			for(Object l : listeners){
				System.out.println(l.toString());
			}
		}
		
		public void addInfoButtonListener(InfoButtonListener listener){
			listenerList.add(InfoButtonListener.class, listener);
		}
		
		public void removeInfoButtonListener(InfoButtonListener listener){
			listenerList.remove(InfoButtonListener.class, listener);
		}
		
	}
	
}

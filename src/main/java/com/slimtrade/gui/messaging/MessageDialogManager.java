package main.java.com.slimtrade.gui.messaging;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.gui.components.PanelWrapper;
import main.java.com.slimtrade.gui.enums.ExpandDirection;

public class MessageDialogManager {
	
	private Point anchorPoint = new Point(0, 500);
	private Dimension defaultSize = new Dimension(400, 40);
	private ExpandDirection expandDirection = ExpandDirection.UP;
	
	private final int bufferSize = 5;
	
	private ArrayList<PanelWrapper> wrapperList = new ArrayList<PanelWrapper>();
	
	public MessageDialogManager(){
		
	}
	
	public void addMessage(TradeOffer trade){
		final MessagePanel panel = new MessagePanel(trade, defaultSize);
		final PanelWrapper wrapper = new PanelWrapper(panel, "SlimTrade Trade Window");
		wrapperList.add(wrapper);
		refreshPanelLocations();
		panel.getCloseButton().addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				wrapperList.remove(wrapper);
				wrapper.dispose();
				refreshPanelLocations();
			}
		});
	}
	
	private void refreshPanelLocations(){
		Point targetPoint = new Point(anchorPoint);
		for(PanelWrapper w : wrapperList){
			w.setLocation(targetPoint);
			if(expandDirection == ExpandDirection.DOWN){
				targetPoint.y += w.getHeight() + bufferSize;
			}else{
				targetPoint.y -= w.getHeight() + bufferSize;
			}
		}
	}
	
}

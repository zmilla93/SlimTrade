package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.macros.CustomMacroRow;

import javax.swing.*;
import java.awt.*;

public class AddRemovePanel extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints gc = new GridBagConstraints();
	private int spacer = 5;

	public AddRemovePanel() {
		this.setLayout(FrameManager.gridBag);
		this.setBackground(new Color(1, 1, 1, 0));
		gc.gridx = 0;
		gc.gridy = 0;
	}

	public void addRemoveablePanel(JPanel panel) {
	    gc.gridy = 0;
        gc.insets.top = 0;
	    for(Component c : this.getComponents()) {
	        if(c.isVisible()) {
	            this.add(c, gc);
                gc.insets.top = spacer;
                gc.gridy++;
            }

        }
		this.add(panel, gc);
		App.eventManager.recursiveColor(panel);
		this.revalidate();
		this.repaint();
	}

	public void refreshPanels() {
	    gc.gridx = 0;
	    gc.gridy = 0;
	    gc.insets.top = 0;
        int i = 0;
        for(Component c : this.getComponents()) {
            if(c.isVisible()) {
                this.add(c, gc);
                gc.insets.top = spacer;
                gc.gridy++;
                i++;
            }
        }
        this.revalidate();
        this.repaint();
    }

	public void clearHiddenPanels() {
		for(Component c : this.getComponents()) {
			if(!c.isVisible()) {
				this.remove(c);
			}
		}
	}

	public void setEnabledAll(boolean state) {
		for(Component c : this.getComponents()) {
			if(c instanceof CustomMacroRow) {
				((CustomMacroRow) c).setEnabledAll(state);
			}
		}
	}

    @Override
    public void updateColor() {
		this.revalidate();
		this.repaint();
    }

}

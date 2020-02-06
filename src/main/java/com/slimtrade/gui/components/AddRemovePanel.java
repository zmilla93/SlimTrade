package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class AddRemovePanel extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints gc = new GridBagConstraints();
	private int spacer = 5;

	public AddRemovePanel() {
//		this.setLayout(FrameManager.gridbag);
//		this.setLayout(new GridBagLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(new Color(1, 1, 1, 0));
		gc.gridx = 0;
		gc.gridy = 0;
        App.eventManager.addListener(this);
        updateColor();
	}

	public void addPanel(JPanel panel) {
	    int i = this.getComponentCount();
        System.out.println("ComponentCount:" + i);
//        gc.gridy = i;
//        gc.insets.top = i == 0 ? 0 : spacer;
		this.add(panel);
		updateColor();
		this.revalidate();
		this.repaint();
	}

	public void saveChanges() {
		for (Component c : this.getComponents()) {
			if (c instanceof RemovablePanel) {
				RemovablePanel panel = (RemovablePanel) c;
				if (panel.isToBeDeleted()) {
					this.remove(panel);
				} else if (panel.isNewPanel()) {
					panel.setNewPanel(false);
				}
			}
		}
        updateColor();
		this.revalidate();
		this.repaint();
	}

	public void revertChanges() {
		for (Component c : this.getComponents()) {
			if (c instanceof RemovablePanel) {
				RemovablePanel panel = (RemovablePanel) c;
				if (panel.isNewPanel()) {
					this.remove(panel);
				} else if (panel.isToBeDeleted()) {
					panel.setVisible(true);
					panel.setToBeDeleted(false);
				}

			}
		}
		updateColor();
		this.revalidate();
		this.repaint();
	}

    @Override
    public void updateColor() {
//	    if(this.getComponentCount() > 0) {
//            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        } else {
//            this.setBorder(null);
//        }
        this.setBorder(null);
        for(Component c : this.getComponents()) {
            if(c.isVisible()){
                this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
                break;
            }
        }

    }
}

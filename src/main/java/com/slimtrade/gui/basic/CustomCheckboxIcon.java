package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CustomCheckboxIcon implements IColorable, Icon {

//    private JCheckbox checkbox;
	private static Border borderDefault;
	private static Border borderRollover;
	private int controlSize = 12;

	protected Color primaryColor;
	protected Color secondaryColor;

	public CustomCheckboxIcon() {
		buildButton();
	}

	public CustomCheckboxIcon(JCheckBox checkbox) {
	    controlSize = checkbox.getHeight();
//        System.out.println("SIZE : " + controlSize);
		buildButton();
	}

	public CustomCheckboxIcon(String text) {
//		super(text);
//		this.model = this.getModel();
		buildButton();
	}
	
	public void setColor(Color color){
		this.primaryColor = color;
	}

	//TODO : Check mouse button?
	//TODO : Currently paints twice per action...
	private void buildButton() {
//		Border outsideBorder = BorderFactory.createLineBorder(Color.GRAY);
//		Border outsideBorderRollover = BorderFactory.createLineBorder(Color.BLACK);
//		Border insideBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
//		borderDefault = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
//		borderRollover = BorderFactory.createCompoundBorder(outsideBorderRollover, insideBorder);
//		this.setBorder(borderDefault);
//		setContentAreaFilled(false);
//		setFocusPainted(false);
//		this.addMouseListener(new MouseListener(){
//			public void mouseClicked(MouseEvent e) {
//			}
//			public void mouseEntered(MouseEvent e) {
//				model.setRollover(true);
//			}
//			public void mouseExited(MouseEvent e) {
//				model.setRollover(false);
//			}
//			public void mousePressed(MouseEvent e) {
//				model.setPressed(true);
//			}
//			public void mouseReleased(MouseEvent e) {
//				model.setPressed(false);
//			}
//		});

	}

	@Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
	    x += 6;
	    y += 6;
        JCheckBox cb = (JCheckBox)c;
        ButtonModel model = cb.getModel();
//        int controlSize = 14;

        boolean drawCheck = model.isSelected();

        if (model.isEnabled()) {
            if (model.isPressed() && model.isArmed()) {
//                g.setColor(UIManager.getColor("MyCheckBox.checkClickBackground"));
                g.setColor(ColorManager.BACKGROUND);
                g.fillRect( x, y, controlSize, controlSize);
            } else {
                g.setColor(ColorManager.LOW_CONTRAST_1);
                g.fillRect( x, y, controlSize, controlSize);
            }
            // Border
            g.setColor(ColorManager.TEXT);
            g.drawRect( x, y, controlSize, controlSize);
        } else {
            g.setColor(Color.BLUE);
            g.drawRect( x, y, controlSize, controlSize);
        }
        // Hover
        if(model.isRollover()) {
            g.setColor(ColorManager.HIGH_CONTRAST_1);
            g.drawRect(x, y, controlSize, controlSize);
        }

        if (model.isSelected()) {
            drawCheck(c, g, x, y);
        }


    }

//    protected void drawCheck(Component c, Graphics g, int x, int y) {
////        int controlSize = 16;
//        g.setColor(ColorManager.TEXT);
//        g.fillRect( x+3, y+5, 2, controlSize-8 );
//        g.drawLine( x+(controlSize-4), y+3, x+5, y+(controlSize-6) );
//        g.drawLine( x+(controlSize-4), y+4, x+5, y+(controlSize-5) );
//    }

    private void drawCheck(Component c, Graphics g, int x, int y) {
//        int controlSize = 16;
        g.setColor(ColorManager.TEXT);
//        g.fillRect( x+3, y+5, 2, controlSize-8 );
        g.fillRect( x+2, y+2, controlSize-3, controlSize-3);
//        g.drawLine( x+(controlSize-4), y+3, x+5, y+(controlSize-6) );
//        g.drawLine( x+(controlSize-4), y+4, x+5, y+(controlSize-5) );
    }

    @Override
    public int getIconWidth() {
        return 20;
    }

    @Override
    public int getIconHeight() {
        return 20;
    }

//    @Override
//	protected void paintComponent(Graphics g) {
//		final Graphics2D g2 = (Graphics2D) g.create();
//		ButtonModel model = getModel();
//		//BORDER
//		if(model.isRollover()){
//			this.setBorder(borderRollover);
//		}else{
//			this.setBorder(borderDefault);
//		}
//		//FILL
////		if(model.isPressed() && model.isRollover()){
////			g2.setPaint(primaryColor);
////		}else if(model.isPressed() && !model.isRollover()){
////			g2.setPaint(Color.LIGHT_GRAY);
////		}else{
////			g2.setPaint(new GradientPaint(new Point(0, 0), secondaryColor, new Point(0, getHeight()), primaryColor));
////		}
//        g2.setPaint(Color.RED);
//		g2.fillRect(0, 0, getWidth(), getHeight());
//		g2.dispose();
//		super.paintComponent(g);
//	}

	@Override
	public void updateColor() {
		primaryColor = ColorManager.PRIMARY;
		secondaryColor = ColorManager.TEXT_EDIT_BACKGROUND;
	}

}

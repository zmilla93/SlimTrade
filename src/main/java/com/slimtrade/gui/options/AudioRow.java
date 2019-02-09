package main.java.com.slimtrade.gui.options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import main.java.com.slimtrade.enums.SoundType;
import main.java.com.slimtrade.gui.buttons.IconButton;

public class AudioRow extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 200;
	private final int HEIGHT = 25;
	private final int BUTTON_SIZE = HEIGHT-5;
	private final int LABEL_WIDTH = 60;
	
	public AudioRow(String title){
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		IconButton sampleButton = new IconButton("/resources/icons/play1.png", BUTTON_SIZE);
		
		JPanel labelPanel = new JPanel(new GridBagLayout());
		labelPanel.setPreferredSize(new Dimension(LABEL_WIDTH, HEIGHT));
		JLabel label = new JLabel(title);
		labelPanel.add(label);
		
		JSlider slider = new JSlider();
		Dimension sliderSize = slider.getPreferredSize();
		sliderSize.height = HEIGHT;
		slider.setPreferredSize(sliderSize);
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setSnapToTicks(true);
		slider.setFocusable(false);
		slider.setPaintTicks(true);
		
		JComboBox<String> soundCombo = new JComboBox<String>();
		soundCombo.setFocusable(false);
		for(SoundType s : SoundType.values()){
			soundCombo.addItem(s.getName());
		}
		
		this.add(sampleButton);
		this.add(labelPanel);
		this.add(slider);
		this.add(soundCombo);
		
	}
	
}

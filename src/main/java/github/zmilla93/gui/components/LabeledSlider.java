package github.zmilla93.gui.components;

import github.zmilla93.core.enums.SliderRange;
import github.zmilla93.core.utility.GUIReferences;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.modules.theme.IFontChangeListener;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class LabeledSlider extends JPanel implements IFontChangeListener {

    public final JSlider slider;
    private final JLabel label = new JLabel();
    private final String suffix;
    private final boolean space;
    private int preferredWidth;

    public LabeledSlider(SliderRange range, String suffix) {
        this(range, suffix, false);
    }

    public LabeledSlider(SliderRange range, String suffix, boolean space) {
        slider = new RangeSlider(range);
        this.suffix = suffix;
        this.space = space;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(slider, gc);
        gc.gridx++;
        gc.insets.left = GUIReferences.SMALL_INSET;
        gc.weightx = 0;
        add(label, gc);

        updateLabel();
        slider.addChangeListener(e -> updateLabel());
        updateWidth();
        ThemeManager.addFontListener(this);
    }

    public void setPreferredWidth(int width) {
        this.preferredWidth = width;
        updateWidth();
    }

    private void updateWidth() {
        setPreferredSize(null);
        if (preferredWidth != -1) setPreferredSize(new Dimension(preferredWidth, getPreferredSize().height));
    }

    private void updateLabel() {
        String spaceText = space ? " " : "";
        label.setText(slider.getValue() + spaceText + suffix);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void onFontChanged() {
        updateWidth();
    }

}

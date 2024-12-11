package github.zmilla93.gui.menubar;

import javax.swing.*;
import java.awt.*;

public class MenubarButton extends JButton {

    public MenubarButton(String text) {
        setText(text);
        int insetVertical = 0;
        int insetHorizontal = 6;
        setMargin(new Insets(insetVertical, insetHorizontal, insetVertical, insetHorizontal));
    }

}

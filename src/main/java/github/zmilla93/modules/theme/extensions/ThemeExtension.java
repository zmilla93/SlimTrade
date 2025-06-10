package github.zmilla93.modules.theme.extensions;

import github.zmilla93.modules.theme.OLD_ThemeColor;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Theme extensions define context specific colors, like "Approve" or "Deny".
 * They can be defined using UIManager keys, or exact color values.
 * This class can be extended to set colors or a per theme bases.
 */
public class ThemeExtension {

    public ColorVariant APPROVE = new ColorVariant("Actions.Green", "Actions.Blue");
    public ColorVariant DENY = new ColorVariant("Actions.Red", "Objects.Pink");
    public ColorVariant INDETERMINATE = new ColorVariant("Actions.Yellow");
    public ColorVariant NEUTRAL = new ColorVariant("Label.foreground");
    public ColorVariant INCOMING_TRADE = new ColorVariant("Actions.Green", "Actions.Blue");
    public ColorVariant OUTGOING_TRADE = new ColorVariant("Actions.Red", "Objects.Pink");
    public ColorVariant SCANNER_MESSAGE = new ColorVariant("Actions.Yellow");
    public ColorVariant UPDATE_MESSAGE = new ColorVariant("Actions.Grey");
    public ColorVariant LABEL_FOREGROUND = new ColorVariant("Label.foreground");
    public ColorVariant BUTTON_BACKGROUND = new ColorVariant("HelpButton.background");
    public ColorVariant BUTTON_FOREGROUND = new ColorVariant("Button.foreground");

    private final HashMap<OLD_ThemeColor, ColorVariant> colorMap = new HashMap<>();
    // FIXME : This should probably be moved elsewhere
    public final HashSet<OLD_ThemeColor> invertTextColor = new HashSet<>();

    public ThemeExtension() {
        buildColorMap();
    }

    protected void buildColorMap() {
        colorMap.clear();
        colorMap.put(OLD_ThemeColor.APPROVE, APPROVE);
        colorMap.put(OLD_ThemeColor.DENY, DENY);
        colorMap.put(OLD_ThemeColor.INDETERMINATE, INDETERMINATE);
        colorMap.put(OLD_ThemeColor.NEUTRAL, NEUTRAL);
        colorMap.put(OLD_ThemeColor.INCOMING_MESSAGE, INCOMING_TRADE);
        colorMap.put(OLD_ThemeColor.OUTGOING_MESSAGE, OUTGOING_TRADE);
        colorMap.put(OLD_ThemeColor.SCANNER_MESSAGE, SCANNER_MESSAGE);
        colorMap.put(OLD_ThemeColor.UPDATE_MESSAGE, UPDATE_MESSAGE);
        colorMap.put(OLD_ThemeColor.LABEL_FOREGROUND, LABEL_FOREGROUND);
        colorMap.put(OLD_ThemeColor.BUTTON_BACKGROUND, BUTTON_BACKGROUND);
        colorMap.put(OLD_ThemeColor.BUTTON_FOREGROUND, BUTTON_FOREGROUND);
    }

    public Color resolve(OLD_ThemeColor themeColor, boolean colorblind) {
        if (!colorMap.containsKey(themeColor)) return Color.RED;
        ColorVariant colorVariant = colorMap.get(themeColor);
        if (colorblind) return colorVariant.colorblindColor();
        return colorVariant.color();
    }

    // A bunch of utility functions for updating keys

    protected void updateKey(OLD_ThemeColor colorKey, String colorName) {
        ColorVariant existingVariant = colorMap.get(colorKey);
        existingVariant.setColor(new ColorOrKey(colorName));
    }

    protected void updateKey(OLD_ThemeColor colorKey, Color color) {
        ColorVariant existingVariant = colorMap.get(colorKey);
        existingVariant.setColor(new ColorOrKey(color));
    }

    protected void updateKey(OLD_ThemeColor colorKey, String colorName, String colorNameCB) {
        ColorVariant existingVariant = colorMap.get(colorKey);
        existingVariant.setColor(new ColorOrKey(colorName));
        existingVariant.setColorCB(new ColorOrKey(colorNameCB));
    }

    protected void updateKey(OLD_ThemeColor colorKey, Color color, Color colorCB) {
        ColorVariant existingVariant = colorMap.get(colorKey);
        existingVariant.setColor(new ColorOrKey(color));
        existingVariant.setColorCB(new ColorOrKey(colorCB));
    }

    protected void updateKey(OLD_ThemeColor color, ColorVariant newVariant) {
        ColorVariant existingVariant = colorMap.get(color);
        if (newVariant.getColorOrKey() != null) existingVariant.setColor(newVariant.getColorOrKey());
        if (newVariant.getColorOrKeyCB() != null) existingVariant.setColorCB(newVariant.getColorOrKeyCB());
    }

}

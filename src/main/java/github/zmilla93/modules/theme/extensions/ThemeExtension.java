package github.zmilla93.modules.theme.extensions;

import github.zmilla93.core.enums.ThemeColor;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Theme extensions define context specific colors, like "Approve" or "Deny".
 * They can be defined using UIManager keys, or exact color values.
 * This class can be extended to set colors or a per theme bases.
 */
public class ThemeExtension {

    /// Approve
    public String APPROVE_KEY = "Actions.Green";
    public String APPROVE_KEY_CB = "Actions.Blue";
    public ColorVariant APPROVE = new ColorVariant(
            new ColorOrKey("Actions.Green"),
            new ColorOrKey("Actions.Blue")
    );
    public ColorVariant DENY = new ColorVariant(
            new ColorOrKey("Actions.Red"),
            new ColorOrKey("Objects.Pink")
    );
    public ColorVariant INDETERMINATE = new ColorVariant(
            new ColorOrKey("Actions.Yellow")
    );
    public ColorVariant INCOMING_TRADE = new ColorVariant(
            new ColorOrKey("Actions.Green"),
            new ColorOrKey("Actions.Blue")
    );
    public ColorVariant OUTGOING_TRADE = new ColorVariant(
            new ColorOrKey("Actions.Red"),
            new ColorOrKey("Objects.Pink")
    );
    public ColorVariant SCANNER_MESSAGE = new ColorVariant(
            new ColorOrKey("Actions.Yellow")
    );
    public ColorVariant UPDATE_MESSAGE = new ColorVariant(
            new ColorOrKey("Actions.Grey")
    );
    /// Deny
    public String DENY_KEY = "Actions.Red";
    public String DENY_KEY_CB = "Objects.Pink";
    /// Indeterminate
    public String INDETERMINATE_KEY = "Actions.Yellow";
    /// Neutral
    public String NEUTRAL_KEY = "Label.foreground";

    /// Message Colors
    public String INCOMING_TRADE_KEY = "Actions.Green";
    public String INCOMING_TRADE_CB_KEY = "Actions.Blue";
    public String OUTGOING_TRADE_KEY = "Actions.Red";
    public String OUTGOING_TRADE_CB_KEY = "Objects.Pink";
    public String SCANNER_MESSAGE_CB_KEY = "Actions.Yellow";
    public String UPDATE_MESSAGE_CB_KEY = "Actions.Grey";

    private final HashMap<ThemeColor, ColorVariant> colorMap = new HashMap<>();

    public HashSet<ThemeColor> invertTextColor = new HashSet<>();

    public ThemeExtension() {
        buildColorMap();
    }

    protected void buildColorMap() {
        colorMap.clear();
        colorMap.put(ThemeColor.APPROVE, APPROVE);
        colorMap.put(ThemeColor.DENY, DENY);
        colorMap.put(ThemeColor.INDETERMINATE, INDETERMINATE);
        colorMap.put(ThemeColor.INCOMING_MESSAGE, INCOMING_TRADE);
        colorMap.put(ThemeColor.OUTGOING_MESSAGE, OUTGOING_TRADE);
        colorMap.put(ThemeColor.SCANNER_MESSAGE, SCANNER_MESSAGE);
        colorMap.put(ThemeColor.UPDATE_MESSAGE, UPDATE_MESSAGE);
    }

    public Color resolve(ThemeColor themeColor, boolean colorblind) {
        if (!colorMap.containsKey(themeColor)) return Color.RED;
        ColorVariant colorVariant = colorMap.get(themeColor);
        if (colorblind) return colorVariant.colorblindColor();
        return colorVariant.color();
    }

//    protected void updateKey(ThemeColor[] colors, ColorVariant newVariant) {
//        for (ThemeColor color : colors) updateKey(color, newVariant);
//    }

//    protected void updateKey(ColorVariant newVariant, ThemeColor... colors) {
//        for (ThemeColor color : colors) updateColorKey(color, newVariant);
//    }

    /**
     * Use this to update an existing colorVariant without overwriting existing values.
     * IE you can change a colorblind variant without overwriting the default normal variant.
     */
    protected void updateKey(ThemeColor color, ColorVariant newVariant) {
        ColorVariant existingVariant = colorMap.get(color);
        if (newVariant.getColorOrKey() != null) existingVariant.setColor(newVariant.getColorOrKey());
        if (newVariant.getColorOrKeyCB() != null) existingVariant.setColorCB(newVariant.getColorOrKeyCB());
    }

}

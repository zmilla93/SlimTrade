package github.zmilla93.modules.theme;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import java.awt.*;

// FIXME : Can theme extensions be made generic by switching this to a class?
public enum Theme {

    // https://github.com/JFormDesigner/FlatLaf/tree/main/flatlaf-intellij-themes#themes
    // Disabled themes are either redundant or hide separators which ruin the look of menus.
    ARK(new FlatArcIJTheme()),
    ARK_ORANGE(new FlatArcOrangeIJTheme()),
    ARK_DARK(new FlatArcDarkIJTheme()),
    ARK_DARK_ORANGE(new FlatArcDarkOrangeIJTheme()),
    CARBON(new FlatCarbonIJTheme()),
    COBALT_2(new FlatCobalt2IJTheme()),
    CYAN_LIGHT(new FlatCyanLightIJTheme()),
    //    DARK_FLAT(new FlatDarkFlatIJTheme()),
    DARK_PURPLE(new FlatDarkPurpleIJTheme()),
    DRACULA(new FlatDraculaIJTheme()),
    GRADIANTO_DARK_FUCHSIA(new FlatGradiantoDarkFuchsiaIJTheme()),
    GRADIANTO_DEEP_OCEAN(new FlatGradiantoDeepOceanIJTheme()),
    GRADIANTO_MIDNIGHT_BLUE(new FlatGradiantoMidnightBlueIJTheme()),
    GRADIANTO_NATURE_GREEN(new FlatGradiantoNatureGreenIJTheme()),
    GRAY(new FlatGrayIJTheme()),
    GRUVBOX_DARK_HARD(new FlatGruvboxDarkHardIJTheme()),
    GRUVBOX_DARK_MEDIUM(new FlatGruvboxDarkMediumIJTheme()),
    GRUVBOX_DARK_SOFT(new FlatGruvboxDarkSoftIJTheme()),
    HIBERBEE_DARK(new FlatHiberbeeDarkIJTheme()),
    HIGH_CONTRAST(new FlatHighContrastIJTheme()),
    //    LIGHT_FLAT(new FlatLightFlatIJTheme()),
    //    LIGHT_OWL(new FlatLightOwlIJTheme()),
    LIGHT_OWL_2(new FlatLightOwlContrastIJTheme()),
    //    NIGHT_OWL(new FlatNightOwlIJTheme()),
    NIGHT_OWL_2(new FlatNightOwlContrastIJTheme()),
    MATERIAL_DESIGN_DARK(new FlatMaterialDesignDarkIJTheme()),
    MONOCAI(new FlatMonocaiIJTheme()),
    //    MONOKAI(new FlatMonokaiProIJTheme()),
    NORD(new FlatNordIJTheme()),
    ONE_DARK(new FlatOneDarkIJTheme()),
    SOLARIZED_LIGHT(new FlatSolarizedLightIJTheme()),
    SOLARIZED_DARK(new FlatSolarizedDarkIJTheme()),
    SPACEGRAY(new FlatSpacegrayIJTheme()),
    VUESION(new FlatVuesionIJTheme()),
    XCODE_DARK(new FlatXcodeDarkIJTheme()),
    ;

    public final IntelliJTheme.ThemeLaf lookAndFeel;
    private String cleanName;

    public final Color red;
    public final Color green;
    public final ThemeExtension extensions;

    Theme(IntelliJTheme.ThemeLaf lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
        this.red = Color.RED;
        this.green = Color.GREEN;
        this.extensions = new ThemeExtension();
    }

    public static Theme getDefaultColorTheme() {
        return Theme.SOLARIZED_LIGHT;
    }

    public boolean isDark() {
        return lookAndFeel.isDark();
    }

    @Override
    public String toString() {
        if (cleanName == null) {
            cleanName = ZUtil.enumToString(name());
        }
        return cleanName;
    }

}

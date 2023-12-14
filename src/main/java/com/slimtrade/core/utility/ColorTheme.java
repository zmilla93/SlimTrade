package com.slimtrade.core.utility;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;
import com.slimtrade.core.enums.ColorThemeType;

public enum ColorTheme {

    // https://github.com/JFormDesigner/FlatLaf/tree/main/flatlaf-intellij-themes#themes
    // Disabled themes are either redundant or hide separators which breaks look of menus.

    ARK(new FlatArcIJTheme(), ColorThemeType.LIGHT),
    ARK_ORANGE(new FlatArcOrangeIJTheme(), ColorThemeType.LIGHT),
    ARK_DARK(new FlatArcDarkIJTheme(), ColorThemeType.DARK),
    ARK_DARK_ORANGE(new FlatArcDarkOrangeIJTheme(), ColorThemeType.DARK),
    CARBON(new FlatCarbonIJTheme(), ColorThemeType.DARK),
//    COBALT_2(new FlatCobalt2IJTheme(), ColorThemeType.DARK),
    CYAN_LIGHT(new FlatCyanLightIJTheme(), ColorThemeType.LIGHT),
//    DARK_FLAT(new FlatDarkFlatIJTheme(), ColorThemeType.DARK),
    DARK_PURPLE(new FlatDarkPurpleIJTheme(), ColorThemeType.DARK),
    DRACULA(new FlatDraculaIJTheme(), ColorThemeType.DARK),
    GRADIANTO_DARK_FUCHSIA(new FlatGradiantoDarkFuchsiaIJTheme(), ColorThemeType.DARK),
    GRADIANTO_DEEP_OCEAN(new FlatGradiantoDeepOceanIJTheme(), ColorThemeType.DARK),
    GRADIANTO_MIDNIGHT_BLUE(new FlatGradiantoMidnightBlueIJTheme(), ColorThemeType.DARK),
    GRADIANTO_NATURE_GREEN(new FlatGradiantoNatureGreenIJTheme(), ColorThemeType.DARK),
    GRAY(new FlatGrayIJTheme(), ColorThemeType.LIGHT),
    GRUVBOX_DARK_HARD(new FlatGruvboxDarkHardIJTheme(), ColorThemeType.DARK),
    GRUVBOX_DARK_MEDIUM(new FlatGruvboxDarkMediumIJTheme(), ColorThemeType.DARK),
    GRUVBOX_DARK_SOFT(new FlatGruvboxDarkSoftIJTheme(), ColorThemeType.DARK),
    HIBERBEE_DARK(new FlatHiberbeeDarkIJTheme(), ColorThemeType.DARK),
    HIGH_CONTRAST(new FlatHighContrastIJTheme(), ColorThemeType.DARK),
//    LIGHT_FLAT(new FlatLightFlatIJTheme(), ColorThemeType.LIGHT),
//    LIGHT_OWL(new FlatLightOwlIJTheme(), ColorThemeType.LIGHT),
    LIGHT_OWL_2(new FlatLightOwlContrastIJTheme(), ColorThemeType.LIGHT),
//    NIGHT_OWL(new FlatNightOwlIJTheme(), ColorThemeType.DARK),
    NIGHT_OWL_2(new FlatNightOwlContrastIJTheme(), ColorThemeType.DARK),
    MATERIAL_DESIGN_DARK(new FlatMaterialDesignDarkIJTheme(), ColorThemeType.DARK),
    MONOCAI(new FlatMonocaiIJTheme(), ColorThemeType.DARK),
    MONOKAI(new FlatMonokaiProIJTheme(), ColorThemeType.DARK),
    NORD(new FlatNordIJTheme(), ColorThemeType.LIGHT),
    ONE_DARK(new FlatOneDarkIJTheme(), ColorThemeType.DARK),
    SOLARIZED_LIGHT(new FlatSolarizedLightIJTheme(), ColorThemeType.LIGHT),
    SOLARIZED_DARK(new FlatSolarizedDarkIJTheme(), ColorThemeType.DARK),
    SPACEGRAY(new FlatSpacegrayIJTheme(), ColorThemeType.DARK),
    VUESION(new FlatVuesionIJTheme(), ColorThemeType.DARK),
    XCODE_DARK(new FlatXcodeDarkIJTheme(), ColorThemeType.DARK),
    ;

    public final IntelliJTheme.ThemeLaf lookAndFeel;
    public final ColorThemeType themeType;
    private String cleanName;

    ColorTheme(IntelliJTheme.ThemeLaf lookAndFeel, ColorThemeType themeType) {
        this.lookAndFeel = lookAndFeel;
        this.themeType = themeType;
    }

    public static ColorTheme getDefaultColorTheme() {
        return ColorTheme.SOLARIZED_LIGHT;
    }

    @Override
    public String toString() {
        if (cleanName == null) {
            cleanName = ZUtil.enumToString(name());
        }
        return cleanName;
    }

}

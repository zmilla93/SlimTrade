package com.slimtrade.core.utility;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;

public enum ColorTheme {

    // https://github.com/JFormDesigner/FlatLaf/tree/main/flatlaf-intellij-themes#themes
    ARK(new FlatArcIJTheme()),
    ARK_ORANGE(new FlatArcOrangeIJTheme()),
    ARK_DARK(new FlatArcDarkIJTheme()),
    ARK_DARK_ORAGE(new FlatArcDarkOrangeIJTheme()),
    CARBON(new FlatCarbonIJTheme()),
    COBALT_2(new FlatCobalt2IJTheme()),
    CYAN_LIGHT(new FlatCyanLightIJTheme()),
    DARK_FLAT(new FlatDarkFlatIJTheme()),
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
    LIGHT_FLAT(new FlatLightFlatIJTheme()),
    LIGHT_OWL(new FlatLightOwlIJTheme()),
    LIGHT_OWL_2(new FlatLightOwlContrastIJTheme()),
    NIGHT_OWL(new FlatNightOwlIJTheme()),
    NIGHT_OWL_2(new FlatNightOwlContrastIJTheme()),
    MATERIAL_DESIGN_DARK(new FlatMaterialDesignDarkIJTheme()),
    MONOCAI(new FlatMonocaiIJTheme()),
    MONOKAI(new FlatMonokaiProIJTheme()),
    NORD(new FlatNordIJTheme()),
    ONE_DARK(new FlatOneDarkIJTheme()),
    SOLARIZED_LIGHT(new FlatSolarizedLightIJTheme()),
    SOLARIZED_DARK(new FlatSolarizedDarkIJTheme()),
    SPACEGRAY(new FlatSpacegrayIJTheme()),
    VUESION(new FlatVuesionIJTheme()),
    ;

    public final IntelliJTheme.ThemeLaf lookAndFeel;

    ColorTheme(IntelliJTheme.ThemeLaf lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }


    @Override
    public String toString() {
        return name();
    }
}

package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.modules.theme.ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

/**
 * Inverts the scanner text color for the given theme. Temp fix for themes with harsh scanner colors.
 */
public class InvertScannerTextExtension extends ThemeExtension {

    public InvertScannerTextExtension() {
        invertTextColor.add(ThemeColor.SCANNER_MESSAGE);
    }

}

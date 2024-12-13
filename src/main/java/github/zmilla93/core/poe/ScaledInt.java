package github.zmilla93.core.poe;

public class ScaledInt {

    // 500, 1000 = 0.5
    public static float getPercentValue(int unscaledValue, int originalComparisonValue) {
        return (float) unscaledValue / originalComparisonValue;
    }

    // 0.5 , 1000
    public static int getScaledValue(float percentageValue, int currentComparisonValue) {
        return Math.round(percentageValue * currentComparisonValue);
    }

}

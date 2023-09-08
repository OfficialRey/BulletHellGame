package sprites;

import java.awt.*;

public class ColorFactor {

    public static final ColorFactor DEFAULT_FACTOR = new ColorFactor(1, 1, 1), FLASH_FACTOR = new ColorFactor(3, 1, 1);

    private static final int MIN_VALUE = 0, MAX_VALUE = 255;

    private final double redFactor, greenFactor, blueFactor;

    public ColorFactor() {
        this.redFactor = 1;
        this.greenFactor = 1;
        this.blueFactor = 1;
    }

    public ColorFactor(double redFactor, double greenFactor, double blueFactor) {
        this.redFactor = redFactor;
        this.greenFactor = greenFactor;
        this.blueFactor = blueFactor;
    }

    public Color factorize(Color color) {
        int red = factorize(color.getRed(), redFactor);
        int blue = factorize(color.getBlue(), blueFactor);
        int green = factorize(color.getGreen(), greenFactor);

        return new Color(red, green, blue);
    }

    public static ColorFactor getGradient(double ratio) {
        double H = ratio * 0.4; // Hue (note 0.4 = Green, see huge chart below)
        double S = 0.9; // Saturation
        double B = 0.9; // Brightness

        Color color = Color.getHSBColor((float) H, (float) S, (float) B);
        return normalize(color);
    }

    public static ColorFactor normalize(Color color) {
        return new ColorFactor((double) color.getRed() / MAX_VALUE, (double) color.getGreen() / MAX_VALUE, (double) color.getBlue() / MAX_VALUE);
    }

    public int factorize(int value, double factor) {
        return (int) Math.max(MIN_VALUE, Math.min((double) value * factor, MAX_VALUE));
    }

    public ColorFactor merge(ColorFactor colorFactor) {
        return new ColorFactor(redFactor + colorFactor.getRedFactor(), greenFactor + colorFactor.getGreenFactor(), blueFactor + colorFactor.getBlueFactor());
    }

    public double getRedFactor() {
        return redFactor;
    }

    public double getGreenFactor() {
        return greenFactor;
    }

    public double getBlueFactor() {
        return blueFactor;
    }

    @Override
    public String toString() {
        return "ColorFactor[r=" + redFactor + ",g=" + greenFactor + ",b=" + blueFactor + "]";
    }
}
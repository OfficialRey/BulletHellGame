package sprites;

import entity.Movable;
import vector.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Sprite extends Movable {
    private static final double HIT_CIRCLE_NORMALIZATION = 0.7;
    protected final BufferedImage image;
    private BufferedImage rotatedImage;
    private final int height, width;

    public Sprite(BufferedImage image, double scale, Vector2D position, Vector2D velocity) {
        super(position, velocity);
        this.image = getImage(image, scale);
        width = this.image.getWidth();
        height = this.image.getHeight();
        this.rotatedImage = this.image;
    }

    public Sprite(BufferedImage image, double scale) {
        this(image, scale, scale);
    }

    public Sprite(BufferedImage image, double scale, ColorFactor colorFactor) {
        this(image, scale, scale, colorFactor);
    }

    public Sprite(BufferedImage image, double xScale, double yScale) {
        this(image, xScale, yScale, ColorFactor.DEFAULT_FACTOR);
    }

    public Sprite(BufferedImage image, double xScale, double yScale, ColorFactor colorFactor) {
        this.image = getImage(image, xScale, yScale, colorFactor);
        width = this.image.getWidth();
        height = this.image.getHeight();
        rotatedImage = this.image;
    }

    public static BufferedImage getImage(BufferedImage image, double scale) {
        return getImage(image, scale, scale);
    }

    public static BufferedImage getImage(BufferedImage image, double xScale, double yScale) {
        return getImage(image, xScale, yScale, ColorFactor.DEFAULT_FACTOR);
    }

    public static BufferedImage getImage(BufferedImage image, double xScale, double yScale, ColorFactor colorFactor) {
        BufferedImage target = new BufferedImage((int) (image.getWidth() * xScale), (int) (image.getHeight() * yScale), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = target.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics2D.drawImage(image, 0, 0, target.getWidth(), target.getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
        graphics2D.dispose();
        target = getColoredImage(target, colorFactor);
        return target;
    }

    private static BufferedImage getColoredImage(BufferedImage image, ColorFactor colorFactor) {
        image = copyImage(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = colorFactor.factorize(new Color(rgb));
                if (!isTransparent(color)) {
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }
        return image;
    }

    private static boolean isTransparent(Color color) {
        return color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0;
    }

    private static BufferedImage getFlashImage(BufferedImage image) {
        return getColoredImage(image, ColorFactor.FLASH_FACTOR);
    }

    public void rotateSprite(int degrees) {
        double rotation = Math.toRadians(degrees);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = result.createGraphics();

        graphics2D.rotate(rotation, (double) width / 2, (double) height / 2);
        graphics2D.drawImage(image, null, 0, 0);
        graphics2D.dispose();

        rotatedImage = result;
    }

    public BufferedImage getImage() {
        return getImage(false);
    }

    public BufferedImage getImage(boolean damaged) {
        if (damaged) {
            return getFlashImage(rotatedImage);
        }
        return rotatedImage;
    }


    public static BufferedImage copyImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public Vector2D getCenterPosition() {
        return new Vector2D(position.getX() + (double) width / 2, position.getY() + (double) height / 2);
    }

    public double getHitCircleRange() {
        return (double) ((width + height) / 4) * HIT_CIRCLE_NORMALIZATION;
    }

    public boolean collides(Sprite other) {
        return getCenterPosition().distance(other.getCenterPosition()) < getHitCircleRange() + other.getHitCircleRange();
    }

    @Override
    public void tick(double deltaTime) {
        move(deltaTime);
    }
}

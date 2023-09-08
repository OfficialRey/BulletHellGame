package gui;

import core.Screen;
import sprites.ColorFactor;
import sprites.Sprite;
import sprites.SpriteSheet;
import util.Util;

import java.awt.image.BufferedImage;

public class ScoreBoard {

    private static final int MAX_MULTIPLIER = 25, MULTIPLIER_DIGITS = 3;
    private static final double MULTIPLIER_SCALE_FACTOR = 0.5;
    private static final int MIN_FACTOR_COLOR = 5;

    private long score;
    private int multiplier;
    private final BufferedImage factorImage;
    private final Sprite[] multiplierImages;
    private final BufferedImage[] scoreImages;
    private final BufferedImage[] numbers;
    private final int digits;
    private final int numberWidth, numberHeight;
    private final double numberScale;

    public ScoreBoard(int digits, SpriteSheet sheet, double numberScale) {
        score = 0;
        this.digits = digits;
        this.numberScale = numberScale;
        factorImage = Sprite.getImage(sheet.getSprite(10, 0), numberScale * MULTIPLIER_SCALE_FACTOR);
        multiplierImages = new Sprite[3];
        scoreImages = new BufferedImage[digits];
        numbers = new BufferedImage[]{
                Sprite.getImage(sheet.getSprite(7, 1), numberScale), // 0
                Sprite.getImage(sheet.getSprite(8, 1), numberScale), // 1
                Sprite.getImage(sheet.getSprite(9, 1), numberScale), // 2
                Sprite.getImage(sheet.getSprite(10, 1), numberScale), // 3
                Sprite.getImage(sheet.getSprite(11, 1), numberScale), // 4
                Sprite.getImage(sheet.getSprite(7, 2), numberScale), // 5
                Sprite.getImage(sheet.getSprite(8, 2), numberScale), // 6
                Sprite.getImage(sheet.getSprite(9, 2), numberScale), // 7
                Sprite.getImage(sheet.getSprite(10, 2), numberScale), // 8
                Sprite.getImage(sheet.getSprite(11, 2), numberScale) // 9
        }; // Initialise all Numbers
        numberWidth = numbers[0].getWidth();
        numberHeight = numbers[0].getHeight();
        multiplier = 1;
        updateScore();
        updateMultiplier();
    }

    public void reset() {
        score = 0;
        updateScore();
        updateMultiplier();
    }

    public void resetMultiplier() {
        multiplier = 1;
        updateMultiplier();
    }

    public void addScore(int amount) {
        score += (long) multiplier * amount;
        multiplier++;
        updateScore();
        updateMultiplier();
    }

    private void updateScore() {
        Util.applyArray(getImages(score, digits), scoreImages);
    }

    private void updateMultiplier() {
        BufferedImage[] spriteContent = getImages(multiplier, MULTIPLIER_DIGITS);

        ColorFactor multiplierColor = ColorFactor.DEFAULT_FACTOR;
        if (multiplier >= MIN_FACTOR_COLOR) {
            float multiplierRatio = (float) 1 - (float) multiplier / (float) MAX_MULTIPLIER;
            if (multiplierRatio < 0) {
                multiplierRatio = 0;
            }
            multiplierColor = ColorFactor.getGradient(multiplierRatio);
            if (multiplier < MIN_FACTOR_COLOR) {
                multiplierColor = multiplierColor.merge(ColorFactor.DEFAULT_FACTOR);
            }
        }
        for (int i = 0; i < MULTIPLIER_DIGITS; i++) {
            multiplierImages[i] = new Sprite(spriteContent[i], numberScale * MULTIPLIER_SCALE_FACTOR * MULTIPLIER_SCALE_FACTOR, multiplierColor);
        }
    }

    public void render(Screen screen) {
        // Render score
        for (int i = 0; i < digits; i++) {
            BufferedImage image = scoreImages[digits - i - 1];
            if (image != null || i == digits - 1) {
                screen.drawImage(image, (int) ((1920 - numberWidth * 1.2) - i * numberWidth), (int) (numberHeight * 1.2));
            }
        }

        // Render multiplier
        int x = (int) ((1920 - numberWidth * 1.2));
        for (int i = 0; i < MULTIPLIER_DIGITS; i++) {
            BufferedImage image = multiplierImages[MULTIPLIER_DIGITS - i - 1].getImage();
            if (image != null || i == MULTIPLIER_DIGITS - 1) {
                x -= numberWidth * MULTIPLIER_SCALE_FACTOR;
                screen.drawImage(image, x, (int) (numberHeight * 2.4));
            }
        }
        x -= numberWidth;
        screen.drawImage(factorImage, x, (int) (numberHeight * 2.4));
    }

    private BufferedImage[] getImages(long value, int arraySize) {
        BufferedImage[] images = new BufferedImage[arraySize];
        for (int i = 0; i < arraySize; i++) {
            int temp = (int) (value % 10);
            images[arraySize - 1 - i] = numbers[temp];
            value /= 10;
        }
        return images;
    }
}
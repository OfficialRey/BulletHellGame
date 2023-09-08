package sprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {
    public static final String TILE_PATH = "tiles_packed.png";
    public static final String SHIP_PATH = "ships_packed.png";

    private final BufferedImage spriteSheet;
    private final BufferedImage[][] sprites;
    private final int rows, columns;
    private final int spriteWidth, spriteHeight;

    public SpriteSheet(String filePath, int rows, int columns, int spriteWidth, int spriteHeight) {
        spriteSheet = loadImage(filePath);
        sprites = new BufferedImage[rows][columns];
        this.rows = rows;
        this.columns = columns;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

        initialise();
    }

    private void initialise() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                sprites[x][y] = extractSprite(x, y);
            }
        }
    }

    private BufferedImage loadImage(String filePath) {
        URL url = getClass().getClassLoader().getResource(filePath);
        try {
            assert url != null;
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage extractSprite(int x, int y) {
        return spriteSheet.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public BufferedImage getSprite(int x, int y) {
        return sprites[x][y];
    }

    public BufferedImage getSprite(Tile tile) {
        return sprites[tile.X][tile.Y];
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }
}
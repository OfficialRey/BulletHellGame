package terrain;

import sprites.Sprite;
import sprites.SpriteSheet;
import sprites.Tile;
import vector.Vector2D;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class World {

    private Vector2D position;

    private final int chunkHeight, width, height, chunks;
    private final Sprite[][] terrain;
    private final SpriteSheet tileSheet;
    private final float xScale, yScale;
    private final int spriteWidth, spriteHeight;
    private final ArrayList<Island> islands;
    private final double travelSpeed;
    private final Random random;
    private int lastIsland;

    public World(int width, int height, Random random, SpriteSheet spriteSheet, int screenWidth, int screenHeight, double travelSpeed, int chunks) {
        this.random = random;
        this.width = width + 2;
        chunkHeight = height;
        this.chunks = chunks;
        this.height = height * chunks;
        islands = new ArrayList<>();
        tileSheet = spriteSheet;
        terrain = new Sprite[this.width][this.height];
        position = new Vector2D(random.nextInt() + Double.MIN_VALUE, Math.abs(random.nextInt() + Double.MIN_VALUE));

        this.travelSpeed = travelSpeed;
        xScale = (float) screenWidth / (width * tileSheet.getSpriteWidth());
        yScale = (float) screenHeight / (height * tileSheet.getSpriteHeight());
        spriteWidth = (int) (tileSheet.getSpriteWidth() * xScale);
        spriteHeight = (int) (tileSheet.getSpriteHeight() * yScale);

        loadIslands();
        createWorld();
    }

    private double lastUpdate;

    public void reset() {
        position = new Vector2D(random.nextInt() + Double.MIN_VALUE, Math.abs(random.nextInt() + Double.MIN_VALUE));
        createWorld();
    }

    public void tick(double deltaTime) {
        double distance = deltaTime * travelSpeed;
        lastUpdate += distance;
        position = position.add(0, distance);
        if (lastUpdate > spriteHeight) {
            lastUpdate -= spriteHeight;
            updateRows();
        }
    }


    private void updateRows() {
        lastIsland--;
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y > 0; y--) {
                terrain[x][y] = terrain[x][y - 1];
            }
        }
        for (int x = 0; x < width; x++) {
            terrain[x][0] = generateSeaTile();
        }
        if (random.nextBoolean()) {
            generateIsland();
        }
    }

    private void loadIslands() {
        URL url = getClass().getClassLoader().getResource("island_designs.dat");
        ArrayList<String> content = new ArrayList<>();
        try {
            assert url != null;
            File file = new File(url.toURI());

            // Read file contents
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine()) != null) {
                content.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < content.size(); i++) {
            String line = content.get(i);
            if (line.startsWith("#")) {
                // The following lines are island data
                ArrayList<String> islandContent = new ArrayList<>();
                for (int j = i + 1; j < content.size(); j++) {
                    line = content.get(j);
                    if (line.startsWith("#")) {
                        i = j + 1;
                        islands.add(new Island(islandContent, random));
                        break;
                    }
                    islandContent.add(line);
                }
            }
        }
    }

    private final Tile[] SEA_TILES = new Tile[]{Tile.SEA0, Tile.SEA1, Tile.SEA2};

    private void generateIsland() {
        if (lastIsland > 0) {
            return;
        }
        Island target = islands.get(random.nextInt(islands.size()));

        int startX = random.nextInt(width - target.getIslandWidth());

        lastIsland = target.getIslandHeight() + random.nextInt(3) + 1;

        for (int x = 0; x < target.getIslandWidth(); x++) {
            for (int y = 0; y < target.getIslandHeight(); y++) {
                Tile tile = target.getTiles()[x][y];
                if (tile != null) {
                    terrain[x + startX][y] = new Sprite(tileSheet.getSprite(tile), xScale, yScale);
                }
            }
        }
    }

    private void createWorld() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                terrain[x][y] = generateSeaTile();
            }
        }
    }

    public void render(Graphics graphics) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height / chunks + 1; y++) {
                Sprite sprite = terrain[x][y + height / chunks - 1];
                if (sprite != null) {
                    graphics.drawImage(sprite.getImage(), x * spriteWidth, (int) (y * spriteHeight + lastUpdate) - spriteHeight, null);
                }
            }
        }
    }

    private Sprite generateSeaTile() {
        Tile tile = Tile.SEA0;
        if (random.nextFloat() < 0.01) {
            tile = SEA_TILES[random.nextInt(SEA_TILES.length)];
        }
        return new Sprite(tileSheet.getSprite(tile), xScale, yScale);
    }
}
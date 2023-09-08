package terrain;

import sprites.Tile;
import vector.Vector2D;

import java.util.List;
import java.util.Random;

public class Island {

    private final Tile[][] tiles;
    private final int islandWidth;
    private final int islandHeight;

    public Island(List<String> content, Random random) {
        this(content, IslandType.GRASS, random);
    }

    public Island(List<String> content, IslandType type, Random random) {

        tiles = new Tile[32][32];
        islandWidth = content.get(0).split("\\|").length;
        islandHeight = content.size();

        int spriteOffset = type == IslandType.GRASS ? 0 : 6;

        // Parse strings to tiles
        for (int y = 0; y < content.size(); y++) {
            String[] lineContent = content.get(y).split("\\|");
            for (int x = 0; x < lineContent.length; x++) {
                String[] values = lineContent[x].strip().split(",");
                if (Integer.parseInt(values[0]) != -1) {
                    tiles[x][y] = getTile(Integer.parseInt(values[0]) + spriteOffset, Integer.parseInt(values[1]));
                }
            }
        }
        if (false) {
            generateBases(random);
        }
    }

    private void generateBases(Random random) {
        Vector2D direction = Vector2D.getUniformed(random);
        Vector2D newPosition;
        Vector2D position = new Vector2D();
        Vector2D oldDirection;
        int x;
        int y;
        boolean done = false;
        while (!done) {
            x = random.nextInt(islandWidth);
            y = random.nextInt(islandHeight);
            if (x < islandWidth - direction.getX() && x > 0) {
                if (y < islandHeight - direction.getY() && y > 0) {
                    if (tiles[x][y] == Tile.GRASS && tiles[x + direction.getIntX()][y + direction.getIntY()] == Tile.GRASS) {
                        done = true;
                        position = new Vector2D(x, y);
                    }
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            newPosition = position.add(direction);
            oldDirection = direction;

            Tile tile = tiles[newPosition.getIntX()][newPosition.getIntY()];
            // Check where last tile was to determine tile piece
            if (tile != Tile.GRASS) {
                // Place end of road
                if (oldDirection.equals(Vector2D.UP)) {
                    tile = Tile.PATH_START_N;
                } else if (oldDirection.equals(Vector2D.DOWN)) {
                    tile = Tile.PATH_START_S;
                } else if (oldDirection.equals(Vector2D.LEFT)) {
                    tile = Tile.PATH_START_W;
                } else if (oldDirection.equals(Vector2D.RIGHT)) {
                    tile = Tile.PATH_START_E;
                }
            }

            if (tile == Tile.GRASS) {
                if (oldDirection.equals(direction)) {
                    if (oldDirection.equals(Vector2D.UP) || oldDirection.equals(Vector2D.DOWN)) {
                        tile = Tile.PATH_UP_DOWN;
                    } else if (oldDirection.equals(Vector2D.LEFT) || oldDirection.equals(Vector2D.RIGHT)) {
                        tile = Tile.PATH_LEFT_RIGHT;
                    }
                }
            }
            tiles[position.getIntX()][position.getIntY()] = tile;
            position = newPosition;
            direction = Vector2D.getUniformed(random);
        }

    }

    private Tile getTile(int x, int y) {
        for (Tile tile : Tile.values()) {
            if (tile.X == x && tile.Y == y) {
                return tile;
            }
        }
        return Tile.SEA0;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getIslandWidth() {
        return islandWidth;
    }

    public int getIslandHeight() {
        return islandHeight;
    }
}
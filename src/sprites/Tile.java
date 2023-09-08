package sprites;

public enum Tile {

    NONE(-1, -1),
    WEED0(0, 3),
    WEED1(0, 4),
    WEED2(0, 5),

    SAND_NW(1, 3),
    SAND_N(2, 3),
    SAND_NE(3, 3),
    SAND_W(1, 4),
    SAND_E(3, 4),
    SAND_SW(1, 5),
    SAND_S(2, 5),
    SAND_SE(3, 5),

    GRASS(2, 4),
    GRASS_NW(4, 3),
    GRASS_NE(5, 3),
    GRASS_SW(4, 4),
    GRASS_SE(5, 4),

    SEA0(6, 3),
    SEA1(4, 5),
    SEA2(5, 5),

    PATH_START_N(4, 8),
    PATH_START_E(5, 9),
    PATH_START_S(4, 9),
    PATH_START_W(5, 8),

    PATH_UP_DOWN(1, 7),
    PATH_LEFT_RIGHT(2, 6);

    public final int X, Y;

    Tile(int x, int y) {
        X = x;
        Y = y;
    }
}
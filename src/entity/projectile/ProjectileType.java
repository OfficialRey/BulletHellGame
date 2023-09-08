package entity.projectile;

public enum ProjectileType {

    SINGLE(750, 0, 0),
    DOUBLE(750, 1, 0),
    LASER(1000, 2, 0),
    FIRE(20, 3, 0),

    BOMB_SINGLE(20, 0, 1),
    BOMB_DOUBLE(20, 1, 1);

    public final int VELOCITY;
    public final int X;
    public final int Y;

    ProjectileType(int velocity, int x, int y) {
        VELOCITY = velocity;
        X = x;
        Y = y;
    }
}
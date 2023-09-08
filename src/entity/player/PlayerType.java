package entity.player;

import vector.Vector2D;

public enum PlayerType {

    FALCON0(100, 1, 750, 35, 0.15, 1, 2, 5, new Vector2D(30, 30), new Vector2D(98, 30)),
    FALCON1(125, 2, 800, 40, 0.1, 1, 1, 5.5, new Vector2D(26, 30), new Vector2D(115, 30)),
    FALCON2(150, 3, 850, 45, 0.075, 1, 0, 6, new Vector2D(30, 30), new Vector2D(128, 30));

    public final int MAX_HEALTH;
    public final int ATTACK_DAMAGE;
    public final int MAX_SPEED;
    public final double ACCELERATION;
    public final double ATTACK_DELAY;
    public final int SPRITE_X, SPRITE_Y;
    public final double SCALE;
    public final Vector2D PROJ_SPAWN_ONE;
    public final Vector2D PROJ_SPAWN_TWO;

    PlayerType(int maxHealth, int attackDamage, int maxSpeed, double acceleration, double attackDelay, int spriteX, int spriteY, double scale, Vector2D projectileSpawnOne, Vector2D projectileSpawnTwo) {
        MAX_HEALTH = maxHealth;
        ATTACK_DAMAGE = attackDamage;
        MAX_SPEED = maxSpeed;
        ACCELERATION = acceleration;
        ATTACK_DELAY = attackDelay;
        SPRITE_X = spriteX;
        SPRITE_Y = spriteY;
        SCALE = scale;
        PROJ_SPAWN_ONE = projectileSpawnOne;
        PROJ_SPAWN_TWO = projectileSpawnTwo;
    }
}

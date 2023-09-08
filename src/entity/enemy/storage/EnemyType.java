package entity.enemy.storage;

public enum EnemyType {
    TEST(),

    WING0(5, 5, 450, 10, 3, 0, 5, 5, 50),
    WING1(4, 5, 300, 10, 2, 3, 5, 5, 25),
    WING2(6, 8, 500, 5, 2, 1, 4, 5, 30),
    TANK0(8, 1, 300, 3, 2, 2, 3, 4, 50),

    GUN_TURRET(10, 1, 1, 5, 1, 3, 50);

    public final int MAX_HEALTH;
    public final int ATTACK_DAMAGE;
    public final int MAX_SPEED;
    public final double ACCELERATION;
    public final double ATTACK_DELAY;
    public final int SPRITE_X, SPRITE_Y;
    public final double SCALE;
    public final int SCORE;

    EnemyType(int maxHealth, int attackDamage, int maxSpeed, double acceleration, double attackDelay, int spriteX, int spriteY, double scale, int score) {
        MAX_HEALTH = maxHealth;
        ATTACK_DAMAGE = attackDamage;
        MAX_SPEED = maxSpeed;
        ACCELERATION = acceleration;
        ATTACK_DELAY = attackDelay;
        SPRITE_X = spriteX;
        SPRITE_Y = spriteY;
        SCALE = scale;
        SCORE = score;
    }

    EnemyType(int maxHealth, int attackDamage, double attackDelay, int spriteX, int spriteY, double scale, int score) {
        MAX_HEALTH = maxHealth;
        ATTACK_DAMAGE = attackDamage;
        MAX_SPEED = 0;
        ACCELERATION = 0;
        ATTACK_DELAY = attackDelay;
        SPRITE_X = spriteX;
        SPRITE_Y = spriteY;
        SCALE = scale;
        SCORE = score;
    }

    EnemyType() {
        MAX_HEALTH = 0;
        ATTACK_DAMAGE = 0;
        MAX_SPEED = 0;
        ACCELERATION = 0;
        ATTACK_DELAY = 0;
        SPRITE_X = 0;
        SPRITE_Y = 0;
        SCALE = 0;
        SCORE = 0;
    }
}
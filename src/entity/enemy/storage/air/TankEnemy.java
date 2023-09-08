package entity.enemy.storage.air;

import core.Game;
import entity.ActorType;
import entity.enemy.storage.EnemyType;
import entity.player.Player;
import entity.projectile.Projectile;
import entity.projectile.ProjectileType;
import sound.SoundPlayer;
import vector.Vector2D;

import java.util.Random;

public class TankEnemy extends AirEnemy {

    private static final int MAX_SHOTS = 5;

    private static final Random random = new Random();
    private int shotCount = 0;

    private Vector2D target;

    public TankEnemy(Game game, Vector2D spawnPosition) {
        super(EnemyType.TANK0, game);
        position = spawnPosition;
    }

    @Override
    public void AI(double deltaTime, Player player) {
        if (target == null || position.distance(target) < 100) {
            target = new Vector2D(random.nextDouble() * 1920, random.nextDouble() * 1080);
        }

        Vector2D enemyToTarget = target.subtract(position);
        accelerate(enemyToTarget);

        Projectile result = launchProjectile(ActorType.ENEMY, ProjectileType.LASER, 3, position, velocity, SoundPlayer.Sound.SHOOT_HARD, 1f);
        if (result != null) {
            if (shotCount < MAX_SHOTS) {
                shotCount++;
                shotDelay = 0.15f;
            } else {
                shotCount = 0;
            }
        }
        rotateToVelocity();
    }
}

package entity.enemy.storage.air;

import core.Game;
import entity.ActorType;
import entity.enemy.storage.EnemyType;
import entity.player.Player;
import entity.projectile.ProjectileType;
import vector.Vector2D;

import java.util.Random;

public class GlideEnemy extends AirEnemy {

    private static final Random random = new Random();
    private final Vector2D target;

    public GlideEnemy(Game game) {
        super(EnemyType.WING1, game);
        position = new Vector2D(random.nextInt(1920), -300);
        target = new Vector2D(random.nextInt(1520) + 200, 1200);
    }

    @Override
    public void AI(double deltaTime, Player player) {
        rotateSprite((int) velocity.angle(Vector2D.UP));
        Vector2D targetVector = target.subtract(position);
        accelerate(targetVector);
        if (position.distance(player.getPosition()) < 800) {
            Vector2D trajectory = player.getCenterPosition().subtract(position);
            launchProjectile(ActorType.ENEMY, ProjectileType.DOUBLE, 3, position, trajectory);
        }
        if (position.getY() > 1180) {
            game.removeEnemy(this);
        }
    }
}
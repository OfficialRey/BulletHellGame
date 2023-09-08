package entity.enemy.storage.air;

import core.Game;
import entity.ActorType;
import entity.enemy.storage.EnemyType;
import entity.player.Player;
import entity.projectile.ProjectileType;
import vector.Vector2D;

import java.util.Random;

public class WingEnemy extends AirEnemy {

    private static final Random random = new Random();

    public WingEnemy(Game game) {
        super(EnemyType.WING0, game);
    }

    public WingEnemy(Game game, Vector2D position) {
        this(game);
        this.position = position;
    }

    private boolean inBounds = false;

    @Override
    public void AI(double deltaTime, Player player) {
        //AI: Keep certain distance
        //Shoot at player
        Vector2D acceleration = new Vector2D();
        double distance = position.distance(player.getPosition());

        if (distance > 700) {
            acceleration = player.getPosition().subtract(position);
        } else if (distance < 400) {
            acceleration = position.subtract(player.getPosition());
        }
        acceleration = acceleration.normalize();
        if (position.getY() > player.getPosition().getY() - 300) {
            acceleration = acceleration.add(Vector2D.UP);
        }

        if (acceleration.equals(new Vector2D())) {
            acceleration = velocity.normalize().add(Vector2D.getRandom());
        }

        // Normalize acceleration to counter right-side movement
        if (random.nextBoolean()) {
            acceleration = acceleration.add(Vector2D.LEFT.divide(2));
        }

        accelerate(acceleration);
        // Rotate towards player
        rotateToPlayer(player);

        shoot(player);
        if (inBounds) {
            stayInBounds();
        } else {
            inBounds = isInBounds();
        }
    }

    private void shoot(Player player) {
        launchProjectile(ActorType.ENEMY, ProjectileType.LASER, 4, position, player.getPosition().subtract(position));
    }
}

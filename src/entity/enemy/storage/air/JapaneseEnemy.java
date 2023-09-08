package entity.enemy.storage.air;

import core.Game;
import entity.enemy.storage.EnemyType;
import entity.player.Player;
import util.Util;
import vector.Vector2D;

public class JapaneseEnemy extends AirEnemy {
    public JapaneseEnemy(Game game, Vector2D spawnPosition) {
        super(EnemyType.WING2, game);
        position = spawnPosition;
        flash(0.2);
    }

    @Override
    public void AI(double deltaTime, Player player) {
        Vector2D vectorToPlayer = player.getPosition().subtract(position).normalize();
        Vector2D randomVector = Vector2D.getRandom().normalize().divide(10);
        Vector2D direction = velocity.normalize().divide(2);
        Vector2D targetVector = randomVector.add(vectorToPlayer).add(direction);

        accelerate(targetVector);
        speedUp(0.7);
        rotateToVelocity();

        collide(player);
    }

    public void collide(Player player) {
        if (collides(player)) {
            Util.createExplosions(game, 6, position);
            player.damage(getAttackDamage());
            destroy();
        }
    }
}

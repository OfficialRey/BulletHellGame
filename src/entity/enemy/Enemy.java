package entity.enemy;

import core.Game;
import entity.Actor;
import entity.ActorType;
import entity.enemy.storage.EnemyType;
import entity.player.Player;
import entity.projectile.ProjectileType;
import vector.Vector2D;

import java.awt.image.BufferedImage;

public abstract class Enemy extends Actor {
    private final EnemyType enemyType;

    public Enemy(BufferedImage image, EnemyType enemyType, Game game) {
        super(image, enemyType.SCALE, enemyType.MAX_SPEED, enemyType.ACCELERATION, enemyType.MAX_HEALTH, enemyType.ATTACK_DAMAGE, enemyType.ATTACK_DELAY, game);
        this.enemyType = enemyType;
    }

    @Override
    public void tick(double deltaTime) {
        AI(deltaTime, game.getPlayer());
    }


    public void shootPlayer(ProjectileType type, double scale, Player player) {
        Vector2D velocity = player.getPosition().subtract(position);
        super.launchProjectile(ActorType.ENEMY, type, scale, getCenterPosition(), velocity);
    }

    public abstract void AI(double deltaTime, Player player);

    public void rotateToPlayer(Player player) {
        rotateToPlayer(player, 0);
    }

    public void rotateToPlayer(Player player, int offset) {
        Vector2D shipToPlayer = player.getPosition().subtract(position);
        rotateSprite(offset + (int) shipToPlayer.angle(Vector2D.UP));
    }

    @Override
    public void destroy() {
        game.addScore(enemyType.SCORE);
        super.destroy();
    }
}

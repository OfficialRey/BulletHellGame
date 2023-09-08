package entity.enemy.storage.ground;

import core.Game;
import entity.ActorType;
import entity.enemy.Enemy;
import entity.enemy.storage.EnemyType;
import entity.player.Player;
import entity.projectile.ProjectileType;
import sprites.Sprite;
import vector.Vector2D;

public abstract class GroundEnemy extends Enemy {

    private final Sprite turretBase;

    public GroundEnemy(EnemyType enemyType, Game game) {
        super(game.getTileSheet().getSprite(enemyType.SPRITE_X, enemyType.SPRITE_Y), enemyType, game);
        turretBase = new Sprite(game.getTileSheet().getSprite(4, 1), enemyType.SCALE);
        position = new Vector2D(position.getX(), 0);
    }

    @Override
    public void AI(double deltaTime, Player player) {
        position = position.add(0, deltaTime);
        position = new Vector2D(400, 400);
        rotateToPlayer(player, 180);
        shootPlayer(ProjectileType.DOUBLE, 2, player);
    }

    public Sprite getTurretBase() {
        return turretBase;
    }
}
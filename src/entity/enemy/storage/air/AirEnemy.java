package entity.enemy.storage.air;

import core.Game;
import entity.enemy.Enemy;
import entity.enemy.storage.EnemyType;

public abstract class AirEnemy extends Enemy {
    public AirEnemy(EnemyType enemyType, Game game) {
        super(game.getShipSheet().getSprite(enemyType.SPRITE_X, enemyType.SPRITE_Y), enemyType, game);
    }
}
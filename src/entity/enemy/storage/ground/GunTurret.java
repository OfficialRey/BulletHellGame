package entity.enemy.storage.ground;

import core.Game;
import entity.enemy.storage.EnemyType;
import vector.Vector2D;

public class GunTurret extends GroundEnemy {
    public GunTurret(Game game, Vector2D spawnPosition) {
        super(EnemyType.GUN_TURRET, game);
        position = spawnPosition;
    }
}
package core;

import entity.enemy.Enemy;
import entity.enemy.storage.EnemyType;
import entity.enemy.storage.air.GlideEnemy;
import entity.enemy.storage.air.JapaneseEnemy;
import entity.enemy.storage.air.TankEnemy;
import entity.enemy.storage.air.WingEnemy;
import entity.enemy.storage.ground.GunTurret;
import vector.Vector2D;

import java.util.ArrayList;
import java.util.Random;

public class EnemyManager {

    private final Game game;
    private final Random random;

    private int maxEnemies;
    ArrayList<Enemy> currentWave;
    private int waveDifficulty;
    private double spawnDelay;

    public EnemyManager(Random random, Game game) {
        this.random = random;
        currentWave = new ArrayList<>();
        waveDifficulty = 0;
        spawnDelay = 0;
        this.game = game;
        maxEnemies = 0;
    }

    public void reset() {
        currentWave.clear();
        waveDifficulty = 0;
        spawnDelay = 0;
    }

    public void tick(double deltaTime) {
        spawnDelay -= deltaTime;
        if (currentWave.isEmpty()) {
            generateWave();
        }
        spawnEnemy();
    }

    public void generateWave() {
        waveDifficulty++;
        currentWave.clear();
        maxEnemies = waveDifficulty + 1;
        int enemyCount = random.nextInt(waveDifficulty) + (waveDifficulty / 2) + 1;
        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = getRandomEnemy();
            if (enemy != null) {
                currentWave.add(enemy);
            }
        }
    }

    private Enemy getRandomEnemy() {
        EnemyType type = EnemyType.values()[random.nextInt(EnemyType.values().length)];
        Vector2D spawnPosition = new Vector2D(random.nextDouble() * 1920, -2000);
        if (type == EnemyType.GUN_TURRET) {
            type = EnemyType.WING0;
        }
        switch (type) {
            case WING0 -> {
                return new WingEnemy(game, spawnPosition);
            }
            case WING1 -> {
                return new GlideEnemy(game);
            }
            case WING2 -> {
                return new JapaneseEnemy(game, spawnPosition);
            }
            case TANK0 -> {
                return new TankEnemy(game, spawnPosition);
            }
            case GUN_TURRET -> {
                return new GunTurret(game, spawnPosition);
            }
            default -> {
                return null;
            }
        }
    }

    private void spawnEnemy() {
        if (spawnDelay > 0 || game.getEnemyCount() >= maxEnemies) {
            return;
        }
        int index = random.nextInt(currentWave.size());
        Enemy enemy = currentWave.remove(index);
        game.addEnemy(enemy);
        spawnDelay = 1 - ((double) waveDifficulty / 10);
        if (spawnDelay < 0.5) {
            spawnDelay = 1 + random.nextInt(2);
        }
        if (currentWave.isEmpty()) {
            spawnDelay = 5;
        }
    }
}
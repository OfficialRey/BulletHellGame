package core;

import entity.Actor;
import entity.ActorType;
import entity.enemy.Enemy;
import entity.explosion.Explosion;
import entity.player.Player;
import entity.player.PlayerType;
import entity.projectile.Projectile;
import gui.ScoreBoard;
import sound.SoundPlayer;
import sprites.Sprite;
import sprites.SpriteSheet;
import terrain.World;
import util.Util;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game {

    private final Screen screen;
    private final World world;
    private final EnemyManager enemyManager;
    private final ScoreBoard scoreBoard;


    private final boolean done;
    private final int targetFps;
    private final long targetDelta;
    private double deltaTime;
    private final SpriteSheet tileSheet;
    private final SpriteSheet shipSheet;
    private final Controls controls;

    private final int maxActors;
    private final Enemy[] enemies; // Ships
    private final Projectile[] projectiles; // projectiles
    private final Explosion[] explosions;
    private Player player;

    public Game(int maxActors, int targetFps) {
        tileSheet = new SpriteSheet(SpriteSheet.TILE_PATH, 12, 10, 16, 16);
        shipSheet = new SpriteSheet(SpriteSheet.SHIP_PATH, 4, 6, 32, 32);

        Random random = new Random();
        screen = new Screen();
        enemyManager = new EnemyManager(random, this);
        world = new World(20, 10, random, tileSheet, screen.getWidth(), screen.getHeight(), 100, 2);
        controls = new Controls();
        screen.addKeyListener(controls);
        scoreBoard = new ScoreBoard(7, tileSheet, 2);

        enemies = new Enemy[maxActors];
        projectiles = new Projectile[maxActors];
        explosions = new Explosion[maxActors];
        this.maxActors = maxActors;

        done = false;
        this.targetFps = targetFps;
        targetDelta = 1000 / targetFps;
        createPlayer();
        SoundPlayer.loopSound(SoundPlayer.Sound.BACKGROUND, 0.5f);
    }

    private void reset() {
        screen.animateClear();

        DELTA_TIME_FACTOR = 1;
        for (int i = 0; i < maxActors; i++) {
            enemies[i] = null;
            projectiles[i] = null;
            explosions[i] = null;
        }
        world.reset();
        enemyManager.reset();
        scoreBoard.reset();

        createPlayer();
    }

    private void createPlayer() {
        player = new Player(PlayerType.FALCON0, this);
    }

    public void updatePlayer(PlayerType type) {
        player = new Player(type, this, player.getPosition());
        Util.createExplosions(this, 10, player.getPosition());
        SoundPlayer.playSound(SoundPlayer.Sound.TRANSFORMATION, 1f);
    }

    public void run() {
        long previousTime;
        while (!done) {
            // Save prev time for FPS
            previousTime = System.currentTimeMillis();

            // Compute and draw objects
            computeCore();
            render();

            // Wait to reach FPS
            capFPS(previousTime);
        }
    }

    private void computeCore() {
        world.tick(deltaTime);
        enemyManager.tick(deltaTime);
        computePlayer();
        computeEnemies();
        computeProjectiles();
        computeExplosions();
        computeCollisions();
    }

    private void computePlayer() {
        player.handleInput(controls.getControls());
        player.tick(deltaTime);
        player.run(deltaTime);
    }


    private void computeEnemies() {
        for (Actor actor : enemies) {
            if (actor != null) {
                actor.tick(deltaTime);
                actor.run(deltaTime);
            }
        }
    }

    private void computeProjectiles() {
        for (Projectile projectile : projectiles) {
            if (projectile != null) {
                projectile.tick(deltaTime);
            }
        }
    }

    private void computeExplosions() {
        for (Explosion explosion : explosions) {
            if (explosion != null) {
                explosion.tick(deltaTime);
            }
        }
    }

    private static final int collisionChecks = 5;
    private int collisionIndex = 0;

    private void computeCollisions() {
        collisionIndex++;
        if (collisionIndex > collisionChecks) {
            collisionIndex = 0;
            // Enemies
            for (Enemy enemy : enemies) {
                if (enemy != null) {
                    for (Projectile projectile : projectiles) {
                        if (projectile != null) {
                            if (projectile.getOwner() == ActorType.PLAYER) {
                                if (projectile.collides(enemy)) {
                                    enemy.damage(player.getAttackDamage());
                                    removeProjectile(projectile);
                                }
                            }
                        }
                    }
                }
            }
            // Player
            for (Projectile projectile : projectiles) {
                if (projectile != null) {
                    if (projectile.getOwner() == ActorType.ENEMY) {
                        if (projectile.collides(player)) {
                            player.damage(player.getAttackDamage());
                            removeProjectile(projectile);
                        }
                    }
                }
            }
        }
    }

    private void render() {
        screen.startRender();

        world.render(screen.getGraphics());
        if (!player.isDead()) {
            screen.drawSprite(player);
        }
        renderExplosions();
        renderEnemies();
        renderProjectiles();
        renderScoreboard();

        screen.stopRender();
    }

    private void renderEnemies() {
        for (Sprite sprite : enemies) {
            if (sprite != null) {
                screen.drawSprite(sprite);
            }
        }
    }

    private void renderProjectiles() {
        for (Projectile projectile : projectiles) {
            if (projectile != null) {
                screen.drawSprite(projectile);
            }
        }
    }

    private void renderScoreboard() {
        scoreBoard.render(screen);
    }

    private void renderExplosions() {
        for (Explosion explosion : explosions) {
            if (explosion != null) {
                screen.drawExplosion(explosion);
            }
        }
    }

    private double DELTA_TIME_FACTOR = 1;

    private void capFPS(long previousTime) {
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - previousTime;
        long distance = targetDelta - delta;
        if (distance > 0 && targetFps != -1) {
            try {
                TimeUnit.MILLISECONDS.sleep((long) (distance * 0.9)); // Waiting shorter for next cycle
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        deltaTime = ((double) System.currentTimeMillis() - (double) previousTime) / 1000; // Normalize to balance game speed
        deltaTime *= DELTA_TIME_FACTOR;
        playerDeathAnimation();
    }

    private void playerDeathAnimation() {
        if (player.isDead()) {
            DELTA_TIME_FACTOR *= 0.99;
            if (DELTA_TIME_FACTOR < 0.01) {
                reset();
            }
        }
    }

    public void addEnemy(Enemy enemy) {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == null) {
                enemies[i] = enemy;
                return;
            }
        }
    }

    public void removeEnemy(Actor actor) {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == actor) {
                enemies[i] = null;
                player.addKill();
                return;
            }
        }
    }

    public int getEnemyCount() {
        int count = 0;
        for (Enemy all : enemies) {
            count += all != null ? 1 : 0;
        }
        return count;
    }

    public void addExplosion(Explosion explosion) {
        for (int i = 0; i < explosions.length; i++) {
            if (explosions[i] == null) {
                explosions[i] = explosion;
                return;
            }
        }
    }

    public void removeExplosion(Explosion explosion) {
        for (int i = 0; i < explosions.length; i++) {
            if (explosions[i] == explosion) {
                explosions[i] = null;
                return;
            }
        }
    }


    public void addProjectile(Projectile projectile) {
        for (int i = 0; i < projectiles.length; i++) {
            if (projectiles[i] == null) {
                projectiles[i] = projectile;
                return;
            }
        }
    }

    public void removeProjectile(Projectile projectile) {
        for (int i = 0; i < projectiles.length; i++) {
            if (projectiles[i] == projectile) {
                projectiles[i] = null;
                return;
            }
        }
    }

    public SpriteSheet getShipSheet() {
        return shipSheet;
    }

    public SpriteSheet getTileSheet() {
        return tileSheet;
    }

    public Player getPlayer() {
        return player;
    }

    public void addScore(int amount) {
        scoreBoard.addScore(amount);
    }

    public void resetScoreboard() {
        scoreBoard.resetMultiplier();
    }
}
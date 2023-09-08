package entity.player;

import core.Controls;
import core.Game;
import entity.Actor;
import entity.ActorType;
import entity.projectile.Projectile;
import entity.projectile.ProjectileType;
import vector.Vector2D;

public class Player extends Actor {
    private static final Vector2D STARTING_POSITION = new Vector2D(960, 700);
    private final PlayerType type;
    private boolean attackSpawn = false;
    private int kills;

    public Player(PlayerType type, Game game) {
        super(game.getShipSheet().getSprite(type.SPRITE_X, type.SPRITE_Y), type.SCALE, type.MAX_SPEED, type.ACCELERATION, type.MAX_HEALTH, type.ATTACK_DAMAGE, type.ATTACK_DELAY, game);
        position = STARTING_POSITION.add((double) -getWidth() / 2, (double) -getHeight() / 2);
        this.type = type;
        kills = 0;
    }

    public Player(PlayerType type, Game game, Vector2D position) {
        super(game.getShipSheet().getSprite(type.SPRITE_X, type.SPRITE_Y), type.SCALE, type.MAX_SPEED, type.ACCELERATION, type.MAX_HEALTH, type.ATTACK_DAMAGE, type.ATTACK_DELAY, game);
        this.position = position;
        this.type = type;
        kills = 0;
    }

    public void tick(double deltaTime) {
        // Rotate plane
        int rotation = (int) (velocity.getX() / maxSpeed * 30);
        rotateSprite(rotation);
        stayInBounds();

        double damageRatio = (double) getHealth() / (double) getMaxHealth();
        if (damageRatio < 0.5) {
            flash(damageRatio);
        }
    }

    public void addKill() {
        kills++;
        switch (kills) {
            case 50 -> game.updatePlayer(PlayerType.FALCON1);
            case 150 -> game.updatePlayer(PlayerType.FALCON2);
        }
    }

    public void handleInput(int[] controls) {
        int x = controls[Controls.RIGHT] - controls[Controls.LEFT];
        int y = controls[Controls.DOWN] - controls[Controls.UP];
        boolean attack = controls[Controls.ATTACK] == 1;
        Vector2D acceleration = new Vector2D(x, y);
        accelerate(acceleration);
        if (attack) {
            attack();
        }
    }


    public void attack() {
        Vector2D spawnPoint = attackSpawn ? type.PROJ_SPAWN_ONE : type.PROJ_SPAWN_TWO;
        Projectile projectile = launchProjectile(ActorType.PLAYER, ProjectileType.SINGLE, 2, getPosition().add(spawnPoint), Vector2D.UP);
        if (projectile != null) {
            attackSpawn = !attackSpawn;
        }
    }

    @Override
    public void damage(int damage) {
        game.resetScoreboard();
        super.damage(damage);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

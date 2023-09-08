package entity;

import core.Game;
import entity.projectile.Projectile;
import entity.projectile.ProjectileType;
import sound.SoundPlayer;
import sprites.Sprite;
import util.Util;
import vector.Vector2D;

import java.awt.image.BufferedImage;

public abstract class Actor extends Sprite {

    public static final double AIR_FRICTION = 0.95;
    public static final double DELAY_NORMALIZATION = 1000;

    protected double maxSpeed;
    private double acceleration;
    private Vector2D accelerationVector;

    protected final Game game;

    private double flashDelay;
    private double flashTimer;

    private int health;
    private final int maxHealth;
    private final int attackDamage;
    private double attackDelay;
    protected int damaged;
    protected double shotDelay;

    public Actor(BufferedImage image, double scale, double maxSpeed, double acceleration, int health, int attackDamage, double attackDelay, Game game) {
        this(image, scale, health, attackDamage, game);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.attackDelay = attackDelay;
        velocity = new Vector2D();
        accelerationVector = new Vector2D();
    }

    public Actor(BufferedImage image, double scale, int health, int attackDamage, Game game) {
        super(image, scale);
        this.game = game;
        velocity = new Vector2D();
        accelerationVector = new Vector2D();

        this.maxHealth = health;
        this.health = health;
        this.attackDamage = attackDamage;
        damaged = 0;
    }

    public void run(double deltaTime) {
        shotDelay -= deltaTime;
        if (accelerationVector.equals(new Vector2D())) {
            velocity = velocity.multiply(AIR_FRICTION);
        }
        if (velocity.magnitude() < 0.2) {
            velocity = new Vector2D();
        }
        move(deltaTime);

        if (isFlashing()) {
            flashTimer += deltaTime;
            if (flashTimer > flashDelay * 2) {
                flashTimer = 0;
            }
        }
    }

    public void rotateToVelocity() {
        rotateSprite((int) velocity.angle(Vector2D.UP));
    }

    public void accelerate(Vector2D accelerationVector) {
        accelerationVector = accelerationVector.normalize();
        this.accelerationVector = accelerationVector;
        if (accelerationVector.equals(new Vector2D())) {
            return;
        }
        velocity = velocity.add(accelerationVector.multiply(acceleration));

        if (velocity.magnitude() > maxSpeed) {
            velocity = velocity.setMagnitude(maxSpeed);
        }
    }

    public void speedUp(double ratio) {
        if (velocity.magnitude() < maxSpeed * ratio) {
            velocity = velocity.setMagnitude(maxSpeed * ratio);
        }
    }

    public Projectile launchProjectile(ActorType owner, ProjectileType type, double scale, Vector2D position, Vector2D velocity) {
        return launchProjectile(owner, type, scale, position, velocity, SoundPlayer.Sound.SHOOT);
    }

    public Projectile launchProjectile(ActorType owner, ProjectileType type, double scale, Vector2D position, Vector2D velocity, SoundPlayer.Sound sound) {
        return launchProjectile(owner, type, scale, position, velocity, sound, 0.3f);
    }

    public Projectile launchProjectile(ActorType owner, ProjectileType type, double scale, Vector2D position, Vector2D velocity, SoundPlayer.Sound sound, float volume) {
        if (shotDelay > 0) {
            return null;
        }
        shotDelay = attackDelay;
        velocity = velocity.add(this.velocity.divide(maxSpeed * 2)); // Add actor momentum to bullet trajectory
        Projectile projectile = new Projectile(owner, game.getTileSheet().getSprite(type.X, type.Y), scale, position, velocity.setMagnitude(type.VELOCITY), game);
        game.addProjectile(projectile);
        SoundPlayer.playSound(sound, volume);
        return projectile;
    }

    @Override
    public BufferedImage getImage() {
        if (isFlashing()) {
            return super.getImage(flashTimer > flashDelay);
        }
        if (damaged > 0) {
            damaged--;
        }
        return super.getImage(damaged > 0);
    }

    public void damage(int damage) {
        damaged = 5;
        health -= damage;
        SoundPlayer.playSound(SoundPlayer.Sound.HIT, 0.7f);
        if (health <= 0) {
            destroy();
        }
    }

    public void destroy() {
        Util.createExplosions(game, 5, position);
        SoundPlayer.playSound(SoundPlayer.Sound.EXPLOSION, 0.85f);
        game.removeEnemy(this);
    }

    public boolean isInBounds() {
        return position.getX() > 0 && position.getX() < 1920 && position.getY() > 0 && position.getY() < 1080;
    }

    public void stayInBounds() {
        if (position.getX() < 0) {
            position = new Vector2D(0, position.getY());
        } else if (position.getX() > 1920 - getWidth()) {
            position = new Vector2D(1920 - getWidth(), position.getY());
        }

        if (position.getY() < 0) {
            position = new Vector2D(position.getX(), 0);
        } else if (position.getY() > 1080 - getWidth()) {
            position = new Vector2D(position.getX(), 1080 - getWidth());
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void flash(double flashDelay) {
        if (flashDelay < 0.3f) {
            flashDelay = 0.3f;
        }
        this.flashDelay = flashDelay;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public boolean isFlashing() {
        return flashDelay > 0;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
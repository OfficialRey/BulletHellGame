package entity.projectile;

import core.Game;
import entity.ActorType;
import sprites.Sprite;
import vector.Vector2D;

import java.awt.image.BufferedImage;

public class Projectile extends Sprite {

    private final ActorType owner;
    private final Game game;

    public Projectile(ActorType owner, BufferedImage image, double scale, Vector2D position, Vector2D velocity, Game game) {
        super(image, scale, position, velocity);
        this.owner = owner;
        this.game = game;
        rotateSprite((int) velocity.angle(Vector2D.UP));
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if (position.getY() < -100 || position.getY() > 1180 || position.getX() < -100 || position.getX() > 2020) {
            game.removeProjectile(this);
        }
    }

    public ActorType getOwner() {
        return owner;
    }
}

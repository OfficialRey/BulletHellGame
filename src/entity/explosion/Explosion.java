package entity.explosion;

import core.Game;
import entity.Movable;
import sprites.Sprite;
import sprites.SpriteSheet;
import vector.Vector2D;

import java.awt.image.BufferedImage;

public class Explosion extends Movable {

    private final Game game;
    private final double animationSpeed;
    private final BufferedImage[] images;
    private double frameCountdown;
    private int frameIndex;

    public Explosion(Vector2D position, Vector2D velocity, double animationSpeed, double scale, Game game) {
        super(position, velocity);
        this.animationSpeed = animationSpeed;
        this.game = game;
        SpriteSheet tileSheet = game.getTileSheet();
        images = new BufferedImage[]{tileSheet.getSprite(4, 0), tileSheet.getSprite(5, 0), tileSheet.getSprite(6, 0), tileSheet.getSprite(7, 0), tileSheet.getSprite(8, 0)};
        for (int i = 0; i < images.length; i++) {
            images[i] = Sprite.getImage(images[i], scale);
        }
        frameIndex = 0;
    }

    @Override
    public void tick(double deltaTime) {
        move(deltaTime);
        frameCountdown += deltaTime;
        if (frameCountdown >= animationSpeed) {
            frameCountdown = 0;
            frameIndex++;
        }
        if (frameIndex == images.length) {
            game.removeExplosion(this);
        }
    }

    public BufferedImage getImage() {
        return images[frameIndex];
    }
}
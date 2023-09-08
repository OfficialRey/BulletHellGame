package util;

import core.Game;
import entity.explosion.Explosion;
import vector.Vector2D;

import java.util.Random;

public class Util {

    private static final int RANDOM_VARIANCE = 3;
    private static final Random random = new Random();

    public static void createExplosions(Game game, int averageAmount, Vector2D position) {
        int explosions = random.nextInt(RANDOM_VARIANCE) + averageAmount - RANDOM_VARIANCE;
        if (explosions < 1) {
            explosions = averageAmount;
        }
        for (int i = 0; i < explosions; i++) {
            double animationSpeed = random.nextDouble() * 0.1 + 0.05;
            double scale = random.nextDouble() * 5 + 2;
            Vector2D newPosition = position.add(Vector2D.getRandom().normalize().multiply(random.nextInt(100)));
            game.addExplosion(new Explosion(newPosition, Vector2D.getRandom(random), animationSpeed, scale, game));
        }
    }

    public static void applyArray(Object[] source, Object[] target) {
        if (source.length == target.length) {
            System.arraycopy(source, 0, target, 0, source.length);
        }
    }

    public static float dbToVolume(float volume) {
        return 20f * (float) Math.log10(volume);
    }
}
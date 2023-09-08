package vector;

import java.util.Random;

public class Vector3D {

    private final double x;
    private final double y;
    private final double z;

    public Vector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D getRandom(Random random) {
        return new Vector3D(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize();
    }

    public Vector3D normalize() {
        double magnitude = magnitude();
        return new Vector3D(x / magnitude, y / magnitude, z / magnitude);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
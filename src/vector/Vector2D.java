package vector;

import java.util.Random;

public class Vector2D {

    private static final Random random = new Random();
    public static final Vector2D UP = new Vector2D(0, -1), DOWN = new Vector2D(0, 1), LEFT = new Vector2D(-1, 0), RIGHT = new Vector2D(1, 0);
    private final double x;
    private final double y;

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D getRandom(Random random) {
        return new Vector2D(random.nextDouble(), random.nextDouble());
    }

    public static Vector2D getRandom() {
        return new Vector2D(1 - random.nextDouble() * 2, 1 - random.nextDouble() * 2);
    }

    public static Vector2D getUniformed(Random random) {
        int targetAxis = random.nextInt(2);
        int value = (int) Math.signum(random.nextDouble() * 2 - 1);
        if (targetAxis == 0) {
            return new Vector2D(value, 0);
        } else {
            return new Vector2D(0, value);
        }
    }

    public Vector2D normalize() {
        return divide(magnitude());
    }

    public Vector2D setMagnitude(double magnitude) {
        return multiply(magnitude / magnitude());
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D add(double x, double y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    public Vector2D add(Vector2D vector2D) {
        return new Vector2D(x + vector2D.x, y + vector2D.y);
    }

    public Vector2D subtract(Vector2D vector2D) {
        return add(-vector2D.getX(), -vector2D.getY());
    }

    public Vector2D subtract(double x, double y) {
        return add(-x, -y);
    }

    public Vector2D multiply(double value) {
        return new Vector2D(x * value, y * value);
    }

    public Vector2D divide(double value) {
        double xVec = x / value;
        double yVec = y / value;
        if (Double.isNaN(xVec)) {
            xVec = 0;
        }
        if (Double.isNaN(yVec)) {
            yVec = 0;
        }
        return new Vector2D(xVec, yVec);
    }

    public boolean equals(Vector2D vector2D) {
        return x == vector2D.getX() && y == vector2D.getY();
    }

    public double angle(Vector2D vector2D) {
        return -180.0 / Math.PI * Math.atan2(vector2D.getX() - x, vector2D.getY() - y);
    }

    public Vector2D swap() {
        return new Vector2D(y, x);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIntX() {
        return (int) x;
    }

    public int getIntY() {
        return (int) y;
    }

    public String toString() {
        return "Vector{" + x + "," + y + "}";
    }

    public double distance(Vector2D vector2D) {
        return subtract(vector2D).magnitude();
    }
}
package entity;

import vector.Vector2D;

public abstract class Movable {

    protected Vector2D position;
    protected Vector2D velocity;

    public Movable() {
        position = new Vector2D();
        velocity = new Vector2D();
    }

    public Movable(Vector2D position, Vector2D velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void accelerate(Vector2D acceleration) {
        velocity = velocity.add(acceleration);
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }


    public abstract void tick(double deltaTime);

    public void move(double deltaTime) {
        position = position.add(velocity.multiply(deltaTime));
    }
}
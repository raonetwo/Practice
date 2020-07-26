package com.andyrew.practice;

public class Ray {

    private Point3 origin;
    private Vec3 direction;

    public void setOrigin(Point3 origin) {
        this.origin = origin;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction;
    }

    public Ray() {
        origin = new Point3(0,0,0);
        direction = new Vec3(0,0,0);
    }

    public Point3 getOrigin() {
        return origin;
    }

    public Vec3 getDirection() {
        return direction;
    }

    public Point3 at(double t) {
        return new Point3(Vec3.add(origin, Vec3.multiply(direction, t)));
    }

    public Ray(Point3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }
}

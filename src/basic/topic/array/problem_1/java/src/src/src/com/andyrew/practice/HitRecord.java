package com.andyrew.practice;

public class HitRecord {
    Point3 p;
    Vec3 normal = new Vec3(0,0,0);
    double t;
    boolean front_face;
    Material material;

    public HitRecord(Point3 p, Vec3 normal, double t) {
        this.p = p;
        this.normal = normal;
        this.t = t;
    }

    public HitRecord() { }

    void setFaceNormal(final Ray r, final Vec3 outwardNormal) {
        front_face = Vec3.dot(r.getDirection(), outwardNormal) < 0;
        normal = front_face ? outwardNormal : outwardNormal.negative();
    }

    public Point3 getP() {
        return p;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public double getT() {
        return t;
    }
}

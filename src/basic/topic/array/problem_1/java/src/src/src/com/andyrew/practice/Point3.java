package com.andyrew.practice;

public class Point3 extends Vec3 {
    public Point3(double x, double y, double z) {
        super(x, y, z);
    }

    public Point3 (Vec3 vec) {
        super(vec.getX(), vec.getY(), vec.getZ());
    }
}

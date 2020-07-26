package com.andyrew.practice;


public class Vec3 {
    private double x;
    private double y;
    private double z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3 add(final Vec3 first, final Vec3 second) {
        return new Vec3(first.x + second.x, first.y + second.y, first.z + second.z);
    }

    public static Vec3 subtract(final Vec3 first, final Vec3 second) {
        return new Vec3(first.x - second.x, first.y - second.y, first.z - second.z);
    }

    public static Vec3 multiply(final Vec3 first, final double scale) {
        return new Vec3(first.x * scale, first.y * scale, first.z * scale);
    }

    public static Vec3 divide(final Vec3 first, final double scale) {
        return new Vec3(first.x / scale, first.y / scale, first.z / scale);
    }

    public static Vec3 multiply(final Vec3 first, final Vec3 second) {
        return new Vec3(first.x * second.x, first.y * second.y, first.z * second.z);
    }

    public static double dot(final Vec3 first, final Vec3 second) {
        return first.x * second.x + first.y * second.y + first.z * second.z;
    }

    public static Vec3 cross(final Vec3 first, final Vec3 second) {
        return new Vec3(first.y * second.z - first.z * second.y, first.z * second.x - first.x * second.z, first.x * second.y - first.y * second.x);
    }

    public static Vec3 unitVector(Vec3 v) {
        return divide(v, v.length());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vec3 add(final Vec3 other) {
        this.x = this.x + other.x;
        this.y = this.y + other.y;
        this.z = this.z + other.z;
        return this;
    }

    public Vec3 negative() {
        return new Vec3(-this.x, -this.y, -this.z);
    }

    public Vec3 scaleUp(final double scale) {
        this.x = this.x * scale;
        this.y = this.y * scale;
        this.z = this.z * scale;
        return this;
    }

    public Vec3 scaleDown(final double scale) {
        this.x = this.x / scale;
        this.y = this.y / scale;
        this.z = this.z / scale;
        return this;
    }

    public double length_squared() {
        return this.x*this.x + this.y*this.y + this.z*this.z;
    }

    public double length() {
        return Math.sqrt(length_squared());
    }

    public static Vec3 random() {
        return new Vec3(Math.random(), Math.random(), Math.random());
    }

    public static Vec3 random(double min, double max) {
        return new Vec3(Math.random()*(max-min) + min, Math.random()*(max-min) + min, Math.random()*(max-min) + min);
    }

    public static Vec3 randomInUnitSphere() {
        while (true) {
            var p = random(-1,1);
            if (p.length_squared() >= 1) {
                continue;
            }
            return p;
        }
    }

    public static Vec3 randomUnitVector() {
        final var a = Math.random()*2*Math.PI;
        final var z = Math.random()*2 - 1;
        final var r = Math.sqrt(1 - z*z);
        return new Vec3(r*Math.cos(a), r*Math.sin(a), z);
    }

    public static Vec3 randomInHemisphere(Vec3 normal) {
        Vec3 unitSphere = randomInUnitSphere();
        if (dot(unitSphere, normal) > 0.0) // In the same hemisphere as the normal
            return unitSphere;
        else
            return unitSphere.negative();
    }

    public static Vec3 reflect(final Vec3 incidence, final Vec3 normal) {
        return subtract(incidence, multiply(normal, 2*dot(incidence,normal)));
    }

    public static Vec3 randomInUnitDisk() {
        while (true) {
            var p = new Vec3(Math.random() * 2 -1, Math.random() * 2 -1, 0);
            if (p.length_squared() >= 1) continue;
            return p;
        }
    }

    public static Vec3 refract(final Vec3 incident, final Vec3 normal, double refrectiveIndexIncidenceOverRefrectiveIndexTransmission) {
        var cos_theta = dot(incident.negative(), normal);
        Vec3 r_out_perp =  add(incident,multiply(normal, cos_theta)).scaleUp(refrectiveIndexIncidenceOverRefrectiveIndexTransmission);
        Vec3 r_out_parallel = multiply(normal, -Math.sqrt(Math.abs(1.0 - r_out_perp.length_squared())));
        return r_out_perp.add(r_out_parallel);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String toString() {
        return "" + this.x + " " + this.y + " " + this.z;
    }

    public void print() {
        System.out.print(this.toString());
    }

    public void println() {
        System.out.println(this.toString());
    }
}

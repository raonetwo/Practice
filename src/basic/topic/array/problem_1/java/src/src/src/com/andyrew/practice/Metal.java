package com.andyrew.practice;

public class Metal implements Material{

    private Color albedo;
    private double fuzz;

    public double getFuzz() {
        return fuzz;
    }

    public Color getAlbedo() {
        return albedo;
    }

    public Metal(Color albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz < 1 ? fuzz : 1;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord record, Color attenuation, Ray scattered) {
        Vec3 reflected = Vec3.reflect(Vec3.unitVector(rayIn.getDirection()), record.normal);
//        scattered = new Ray(record.p, reflected);
//        attenuation = albedo;
        scattered.setOrigin(record.p);
        scattered.setDirection(reflected.add(Vec3.randomInUnitSphere().scaleUp(fuzz)));
        attenuation.setX(albedo.getX());
        attenuation.setY(albedo.getY());
        attenuation.setZ(albedo.getZ());
        return (Vec3.dot(scattered.getDirection(), record.normal) > 0);
    }
}

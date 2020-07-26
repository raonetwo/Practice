package com.andyrew.practice;

public class Lambertian implements Material{

    private Color albedo;

    public Lambertian(Color albedo) {
        this.albedo = albedo;
    }

    public Color getAlbedo() {
        return albedo;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord record, Color attenuation, Ray scattered) {
        Vec3 scatterDirection = Vec3.add(record.normal, Vec3.randomUnitVector());
//        scattered = new Ray(record.p, scatterDirection);
//        attenuation = albedo;
        scattered.setOrigin(record.p);
        scattered.setDirection(scatterDirection);
        attenuation.setX(albedo.getX());
        attenuation.setY(albedo.getY());
        attenuation.setZ(albedo.getZ());
        return true;
    }
}

package com.andyrew.practice;

public class Dielectric implements Material{

    double refrectiveIndex;

    public double getRefrectiveIndex() {
        return refrectiveIndex;
    }

    public Dielectric(double refrectiveIndex) {
        this.refrectiveIndex = refrectiveIndex;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord record, Color attenuation, Ray scattered) {

        attenuation.setX(1.0);
        attenuation.setY(1.0);
        attenuation.setZ(1.0);
        double etai_over_etat = record.front_face ? (1.0 / refrectiveIndex) : refrectiveIndex;
        Vec3 unit_direction = Vec3.unitVector(rayIn.getDirection());
        double cos_theta = Math.min(Vec3.dot(unit_direction.negative(), record.normal), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta*cos_theta);
        if (etai_over_etat * sin_theta > 1.0 ) {
            Vec3 reflected = Vec3.reflect(unit_direction, record.normal);
            scattered.setOrigin(record.p);
            scattered.setDirection(reflected);
            return true;
        }
        double reflect_prob = schlick(cos_theta, etai_over_etat);
        if (Math.random() < reflect_prob)
        {
            Vec3 reflected = Vec3.reflect(unit_direction, record.normal);
            scattered.setOrigin(record.p);
            scattered.setDirection(reflected);
            return true;
        }
        Vec3 refracted = Vec3.refract(unit_direction, record.normal, etai_over_etat);
        scattered.setOrigin(record.p);
        scattered.setDirection(refracted);
        return true;
    }

    private double schlick(double cos_theta, double ref_id) {
        var r0 = (1-ref_id) / (1+ref_id);
        r0 = r0*r0;
        return r0 + (1-r0)*Math.pow((1 - cos_theta),5);
    }
}

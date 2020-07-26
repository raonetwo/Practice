package com.andyrew.practice;

public class Sphere implements Hittable {
    Point3 center;
    double radius;
    Material material;
    public Sphere(final Point3 center, final double radius, final Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public Point3 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public Material getMaterial() {
        return material;
    }


    @Override
    public boolean hit(Ray r, double t_min, double t_max, HitRecord rec) {
        Vec3 oc = Vec3.add(r.getOrigin(), center.negative());
        var a = r.getDirection().length_squared();
        var half_b = Vec3.dot(oc, r.getDirection());
        var c = oc.length_squared() - radius * radius;
        var quarterDiscriminant = half_b * half_b - a * c;

        if (quarterDiscriminant > 0) {
            var root = Math.sqrt(quarterDiscriminant);

            var temp = (-half_b - root) / a;
            if (temp < t_max && temp > t_min) {
                rec.t = temp;
                rec.p = r.at(rec.t);
                Vec3 outward_normal = Vec3.subtract(rec.p, center).scaleDown(radius);
                rec.setFaceNormal(r, outward_normal);
                rec.material = material;
                return true;
            }

            temp = (-half_b + root) / a;
            if (temp < t_max && temp > t_min) {
                rec.t = temp;
                rec.p = r.at(rec.t);
                Vec3 outward_normal = Vec3.subtract(rec.p, center).scaleDown(radius);
                rec.setFaceNormal(r, outward_normal);
                rec.material = material;
                return true;
            }
        }

        return false;
    }


//    private static double hitSphere(Point3 center, double radius, Ray r) {
//        // Points that lie on sphere are the points P that satisfy (P - C).(P - C) = r^2 where C is the center of the sphere
//        // (x - Cx)^2 + (y - Cy)^2 + (z - Cz)^2 = r^2 now a point can be represented as a ray from origin as origin + t * unit_vector_direction
//        // or origin + t * direction which we had earlier defined in a ray. So a point P(t) can lie on sphere if (P(t)−C)⋅(P(t)−C)=r2
//        // (O+tb−C)⋅(O+tb−C)=r^2 or (O-C + tb).(O-C + tb) on expanding t^2*b⋅b+2tb⋅(O−C)+(O−C)⋅(O−C)−r^2=0
//        // here we want to solve for t as we know b for all rays we know O and C and see oif solution is possible i.e. some t exists for ray O + t b
//        // Get a vector from center to origin (O-C)
//        Vec3 oc = Vec3.add(r.getOrigin(), center.negative());
//        // dot product of direction of ray b.b
//        var a = r.getDirection().length_squared();
//        // 2 * (O-C).b
////        var b = 2.0 * Vec3.dot(oc, r.getDirection());
//        var half_b = Vec3.dot(oc, r.getDirection());
//        // (O-C).(O-C) - r^2
//        var c = oc.length_squared()- radius*radius;
//        // so for the quadratic equation above solving for t a root exists only if the discriminant is positive
////        var discriminant = b*b - 4*a*c;
//        var quarterDiscriminant = half_b*half_b - a*c;
//        if (quarterDiscriminant < 0) {
//            return -1.0;
//        } else {
////            return (-b - Math.sqrt(discriminant) ) / (2.0*a);
//            return (-half_b - Math.sqrt(quarterDiscriminant) ) / (a);
//        }
//    }
}

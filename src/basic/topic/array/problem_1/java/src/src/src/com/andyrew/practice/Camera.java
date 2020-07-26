package com.andyrew.practice;

public class Camera {

    //    private final double focal_length = 1.0;
    private final Point3 origin;
    private final Vec3 lower_left_corner;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final double lensRadius;
    private final Vec3 u;
    private final Vec3 v;
    private final Vec3 w;

    public Camera(
            final Point3 lookfrom,
            final Point3 lookat,
            final Vec3 vup,
            final double vetricalFieldOfViewInDegrees,
            final double aspect_ratio,
            final double aperture,
            final double focus_dist) {

        double cameraFieldOfViewInRadians = Math.toRadians(vetricalFieldOfViewInDegrees);
        double viewport_height = 2.0 * Math.tan(cameraFieldOfViewInRadians / 2);
        double viewport_width = aspect_ratio * viewport_height;

        w = Vec3.unitVector(Vec3.subtract(lookfrom, lookat));
        u = Vec3.unitVector(Vec3.cross(vup, w));
        v = Vec3.cross(w, u);

        origin = lookfrom;

        horizontal = Vec3.multiply(u, viewport_width * focus_dist);
        vertical = Vec3.multiply(v, viewport_height * focus_dist);
        lower_left_corner = Vec3.subtract(origin, Vec3.divide(horizontal, 2)).add(Vec3.divide(vertical, 2).negative()).add(w.scaleUp(focus_dist).negative());
        lensRadius = aperture / 2;
    }

    // Lower left corner is origin - horizontal/2 - vertical/2 - vec3(0, 0, focal_length)
    // if origin is 0,0,0 viewport's midpoint lies focal length distance away in -z axis
    // - horizontal/2 and -vertical/2 gives x and y projection vectors for the lower left corner
//    private Vec3 lower_left_corner = Vec3.subtract(origin, Vec3.divide(horizontal,2)).
//            add(Vec3.divide(vertical, 2).negative()).add(new Vec3(0, 0, focal_length).negative());

    Ray getRay(double s, double t) {
        Vec3 rd = Vec3.randomInUnitDisk().scaleUp(lensRadius);
        Vec3 offset = Vec3.multiply(u, rd.getX()).add(Vec3.multiply(v, rd.getY()));
        return new Ray(new Point3(Vec3.add(origin, offset)),
                Vec3.add(lower_left_corner, Vec3.multiply(horizontal, s)).add(Vec3.multiply(vertical, t)).add(origin.negative()).add(offset.negative()));
    }
}

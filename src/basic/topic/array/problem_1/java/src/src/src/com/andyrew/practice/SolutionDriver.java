package com.andyrew.practice;

public class SolutionDriver {

    public static void main(String[] args) {

        // Image
        final int samples_per_pixel = 500;
        final double aspect_ratio = 3.0 / 2.0;
        final int image_width = 1200;
        final int image_height = (int)(image_width / aspect_ratio);
        final int maxDepth = 50;

        // World
        HittableList world = randomScene();

        // Camera
        Point3 lookfrom = new Point3(13,2,3);
        Point3 lookat = new Point3(0,0,0);
        Vec3 vup = new Vec3(0,1,0);
//        var dist_to_focus = Vec3.subtract(lookfrom,lookat).length();
        var dist_to_focus = 10.0;
        var aperture = 0.1;

        Camera camera =  new Camera(lookfrom, lookat, vup, 20, aspect_ratio, aperture, dist_to_focus);

        // Render

        System.out.println("P3\n" + image_width + ' ' + image_height + "\n255");

        for (int j = image_height-1; j >= 0; --j) {
            for (int i = 0; i < image_width; ++i) {
                final Color pixelColor = new Color(0, 0, 0);
                for (int s = 0; s < samples_per_pixel; ++s) {
                    var u = (i + Math.random()) / (image_width-1);
                    var v = (j + Math.random()) / (image_height-1);
                    Ray r = camera.getRay(u, v);
                    pixelColor.add(rayColor(r, world, maxDepth));
                }
                Color.writeColor(System.out, pixelColor, samples_per_pixel);
            }
        }
    }

    public static Color rayColor(final Ray r, HittableList world, int depth) {
        HitRecord hitRecord = new HitRecord();
        if (depth <= 0)
            return new Color(0,0,0);

        if (world.hit(r, 0.001, Double.POSITIVE_INFINITY, hitRecord)) {
//            Point3 target = new Point3(Vec3.add(hitRecord.p, hitRecord.normal).add(Vec3.randomUnitVector()));
            Ray scattered = new Ray();
            Color attenuation = new Color();
            if (hitRecord.material.scatter(r, hitRecord, attenuation, scattered)) {
                return new Color(Vec3.multiply(attenuation, rayColor(scattered, world, depth-1)));
            }
            return new Color(0,0,0);
        }
        // get a unit vector in the direction of the ray
        Vec3 unit_direction = Vec3.unitVector(r.getDirection());
        // get the y coord for the unit vector and offset it by 1 since viewport max coordinate can be -1 and we want color to be positive
        // and finally scale by 0.5 since max value can be 1 and we know color value lies b/w 0 and 1
        double t = 0.5*(unit_direction.getY() + 1.0);
        // mix white with blue at bottom y is small hence t is small
        // standard linear interpolation or lerp
        // blendedValue=(1−t)⋅startValue+t⋅endValue
        return new Color(Vec3.add(new Color(1.0, 1.0, 1.0).scaleUp((1.0-t)), new Color(0.5, 0.7, 1.0).scaleUp(t)));
    }

    public static HittableList randomScene() {
        HittableList world = new HittableList();

        var ground_material = new Lambertian(new Color(0.5, 0.5, 0.5));
        world.getHittableList().add(new Sphere(new Point3(0,-1000,0), 1000, ground_material));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                var choose_mat = Math.random();
                Point3 center = new Point3(a + 0.9*Math.random(), 0.2, b + 0.9*Math.random());

                if (Vec3.subtract(center, new Point3(4, 0.2, 0)).length() > 0.9) {
                    Material sphere_material;

                    if (choose_mat < 0.8) {
                        // diffuse
                        var albedo = Vec3.multiply(Color.random(), Color.random());
                        sphere_material = new Lambertian(new Color(albedo));
                        world.getHittableList().add(new Sphere(center, 0.2, sphere_material));
                    } else if (choose_mat < 0.95) {
                        // metal
                        var albedo = new Color(Color.random(0.5, 1));
                        var fuzz = Math.random() * 0.5;
                        sphere_material = new Metal(albedo, fuzz);
                        world.getHittableList().add(new Sphere(center, 0.2, sphere_material));
                    } else {
                        // glass
                        sphere_material = new Dielectric(1.5);
                        world.getHittableList().add(new Sphere(center, 0.2, sphere_material));
                    }
                }
            }
        }

        var material1 = new Dielectric(1.5);
        world.getHittableList().add(new Sphere(new Point3(0, 1, 0), 1.0, material1));

        var material2 = new Lambertian(new Color(0.4, 0.2, 0.1));
        world.getHittableList().add(new Sphere(new Point3(-4, 1, 0), 1.0, material2));

        var material3 = new Metal(new Color(0.7, 0.6, 0.5), 0.0);
        world.getHittableList().add(new Sphere(new Point3(4, 1, 0), 1.0, material3));

        return world;
    }

}

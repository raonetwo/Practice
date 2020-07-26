package com.andyrew.practice;

import java.io.PrintStream;

public class Color extends Vec3 {
    public Color(double x, double y, double z) {
        super(x, y, z);
    }

    public Color() {
        super(0,0,0);
    }

    public Color (Vec3 vec) {
        super(vec.getX(), vec.getY(), vec.getZ());
    }

    public static void writeColor(PrintStream out, Color pixelColor, int samples_per_pixel) {
        var r = pixelColor.getX();
        var g = pixelColor.getY();
        var b = pixelColor.getZ();

        // Divide the color by the number of samples and gamma-correct for gamma=2.0.
        var scale = 1.0 / samples_per_pixel;
        r = Math.sqrt(scale * r);
        g = Math.sqrt(scale * g);
        b = Math.sqrt(scale * b);
        // Write the translated [0,255] value of each color component.
        out.println("" + (int)(255.999 * clamp(r, 0, 0.999)) + " "
                + (int)(255.999 * clamp(g, 0, 0.999)) + " "
                + (int)(255.999 * clamp(b, 0, 0.999)));
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.min(maximum, Math.max(minimum, value));
    }
}

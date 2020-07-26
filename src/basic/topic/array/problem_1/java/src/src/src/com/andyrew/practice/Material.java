package com.andyrew.practice;

public interface Material {
    boolean scatter(final Ray rayIn, final HitRecord record, final Color attenuation, final Ray scattered);
}
